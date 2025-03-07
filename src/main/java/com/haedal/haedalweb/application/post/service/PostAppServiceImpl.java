package com.haedal.haedalweb.application.post.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.haedal.haedalweb.application.post.dto.BasePostRequestDto;
import com.haedal.haedalweb.application.post.dto.BasePostResponseDto;
import com.haedal.haedalweb.application.post.dto.BasePostSummaryResponseDto;
import com.haedal.haedalweb.application.post.dto.PostImageResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardRequestDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardSummaryResponseDto;
import com.haedal.haedalweb.application.post.mapper.PostImageMapper;
import com.haedal.haedalweb.application.post.mapper.PostMapper;
import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.board.service.BoardService;
import com.haedal.haedalweb.domain.comment.service.CommentService;
import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostImage;
import com.haedal.haedalweb.domain.post.model.PostType;
import com.haedal.haedalweb.domain.post.service.PostImageService;
import com.haedal.haedalweb.domain.post.service.PostService;
import com.haedal.haedalweb.domain.post.service.ViewRecordService;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.infrastructure.image.ImageRemoveEvent;
import com.haedal.haedalweb.infrastructure.image.ImageSaveRollbackEvent;
import com.haedal.haedalweb.infrastructure.image.ImageUtil;
import com.haedal.haedalweb.security.service.SecurityService;

@Service
public class PostAppServiceImpl implements PostAppService {
	private final PostImageService postImageService;
	private final PostService postService;
	private final CommentService commentService;
	private final BoardService boardService;
	private final ViewRecordService viewRecordService;
	private final SecurityService securityService;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final String uploadPath;
	private final String uploadUrl;

	public PostAppServiceImpl(PostImageService postImageService, PostService postService, CommentService commentService,
		BoardService boardService, ViewRecordService viewRecordService, SecurityService securityService,
		ApplicationEventPublisher applicationEventPublisher,
		@Value("${file.path.upload-post-images}") String uploadPath,
		@Value("${file.url.upload-post-images}") String uploadUrl) {
		this.postImageService = postImageService;
		this.postService = postService;
		this.commentService = commentService;
		this.boardService = boardService;
		this.viewRecordService = viewRecordService;
		this.securityService = securityService;
		this.applicationEventPublisher = applicationEventPublisher;
		this.uploadPath = uploadPath;
		this.uploadUrl = uploadUrl;
	}

	@Override
	@Transactional
	public void registerPost(Long boardId,
		PostWithBoardRequestDto postWithBoardRequestDto) { // Board가 존재하는 Post 생성 (활동 게시판의 게시글)
		Board board = boardService.getBoard(boardId);
		User user = securityService.getLoggedInUser();

		Post post = Post.builder()
			.title(postWithBoardRequestDto.getPostTitle())
			.content(postWithBoardRequestDto.getPostContent())
			.postType(PostType.ACTIVITY)
			.activityStartDate(postWithBoardRequestDto.getPostActivityStartDate())
			.activityEndDate(postWithBoardRequestDto.getPostActivityEndDate())
			.user(user)
			.board(board)
			.build();

		postService.registerPost(post);

		List<String> postImageNames = postWithBoardRequestDto.getPostImageNames();
		postImageService.addPostImagesToPost(postImageNames, post); // PostImage에 Post를 연결해주기
	}

	@Override
	@Transactional
	public void registerPost(PostType postType, BasePostRequestDto basePostRequestDto) { // Board가 필요없는 Post 생성
		User user = securityService.getLoggedInUser();

		Post post = Post.builder()
			.title(basePostRequestDto.getPostTitle())
			.content(basePostRequestDto.getPostContent())
			.postType(postType)
			.user(user)
			.build();

		postService.registerPost(post);
		List<String> postImageNames = basePostRequestDto.getPostImageNames();
		postImageService.addPostImagesToPost(postImageNames, post); // PostImage에 Post를 연결해주기
	}

	@Override
	@Transactional
	public PostImageResponseDto registerPostImage(MultipartFile postImageFile) { // PostImage 생성
		User loggedInUser = securityService.getLoggedInUser();

		String originalFile = postImageFile.getOriginalFilename();
		String saveFile = UUID.randomUUID() + "." + ImageUtil.getExtension(originalFile);
		ImageUtil.uploadImage(postImageFile, uploadPath, saveFile);

		applicationEventPublisher.publishEvent(new ImageSaveRollbackEvent(uploadPath, saveFile));

		PostImage postImage = PostImage.builder()
			.originalFile(originalFile)
			.saveFile(saveFile)
			.user(loggedInUser)
			.build();

		PostImage savedPostImage = postImageService.registerPostImage(postImage);

		return PostImageMapper.toDto(ImageUtil.generateImageUrl(uploadUrl, saveFile), savedPostImage);
	}

	@Override
	@Transactional
	public void removePost(Long boardId, Long postId) { // 활동 게시판의 게시글 삭제
		Post post = postService.getPostWithUserAndBoard(boardId, postId);

		User loggedInUser = securityService.getLoggedInUser();
		User postCreator = post.getUser();
		User boardCreator = post.getBoard().getUser();

		postService.validateAuthorityOfBoardPostManagement(loggedInUser, postCreator, boardCreator); // 게시글 삭제 검증

		// 관련된 댓글 모두 삭제
		commentService.removeComments(postId);

		// 저장된 PostImage 파일 이름 저장
		List<PostImage> postImages = postImageService.getPostImages(post);

		if (!postImages.isEmpty()) {
			List<String> removeFiles = postImages.stream()
				.map(PostImage::getSaveFile)
				.toList();

			postImageService.removePostImages(postImages);

			for (String removeFile : removeFiles) { // 성능테스트 이후에 느리다면, Batch Event 로 최적화 해야 함.
				applicationEventPublisher.publishEvent(new ImageRemoveEvent(uploadPath, removeFile));
			}
		}
		postService.removePost(post);
	}

	@Override
	@Transactional
	public void removePost(PostType postType, Long postId) { // Board가 필요없는 Post 삭제
		Post post = postService.getPostByPostTypeAndId(postType, postId);

		// Notice 말고, 다른 PostType이 생긴다면 삭제 검증을 해야 함.

		// 관련된 댓글 모두 삭제
		commentService.removeComments(postId);

		// 저장된 PostImage 파일 이름 저장
		List<PostImage> postImages = postImageService.getPostImages(post);

		if (!postImages.isEmpty()) {
			List<String> removeFiles = postImages.stream()
				.map(PostImage::getSaveFile)
				.toList();

			postImageService.removePostImages(postImages);

			for (String removeFile : removeFiles) { // 성능테스트 이후에 느리다면, Batch Event 로 최적화 해야 함.
				applicationEventPublisher.publishEvent(new ImageRemoveEvent(uploadPath, removeFile));
			}
		}
		postService.removePost(post);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PostWithBoardSummaryResponseDto> getPostPage(Long boardId,
		Pageable pageable) { // board가 존재하는 post (활동 게시글) 페이징
		Page<Post> postPage = postService.getPostPage(boardId, pageable);

		return postPage.map(PostMapper::toPostWithBoardSummaryResponseDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BasePostSummaryResponseDto> getPostPage(PostType postType, Pageable pageable) {
		Page<Post> postPage = postService.getPostPage(postType, pageable);

		return postPage.map(PostMapper::toBasePostSummaryResponseDto);
	}

	@Override
	@Transactional
	public PostWithBoardResponseDto getPost(Long boardId, Long postId, String clientIp) {
		handleViewCount(postId, clientIp);
		Post post = postService.getPostWithUserAndBoard(boardId, postId);
		return PostMapper.toPostWithBoardResponseDto(post);
	}

	@Override
	@Transactional
	public BasePostResponseDto getPost(PostType postType, Long postId, String clientIp) {
		handleViewCount(postId, clientIp);
		Post post = postService.getPostByPostTypeAndId(postType, postId);
		return PostMapper.toBasePostResponseDto(post);
	}

	@Override
	@Transactional
	public void updatePost(Long boardId, Long postId, PostWithBoardRequestDto postWithBoardRequestDto) {
		Post post = postService.getPostWithUserAndBoard(boardId, postId);

		User loggedInUser = securityService.getLoggedInUser();
		User postCreator = post.getUser();
		User boardCreator = post.getBoard().getUser();

		postService.validateAuthorityOfBoardPostManagement(loggedInUser, postCreator, boardCreator); // 게시글 수정 검증

		post.setTitle(postWithBoardRequestDto.getPostTitle());
		post.setContent(postWithBoardRequestDto.getPostContent());
		post.setActivityStartDate(postWithBoardRequestDto.getPostActivityStartDate());
		post.setActivityEndDate(postWithBoardRequestDto.getPostActivityEndDate());

		if (postWithBoardRequestDto.getPostImageNames() != null && !postWithBoardRequestDto.getPostImageNames()
			.isEmpty()) {
			updatePostImages(postWithBoardRequestDto.getPostImageNames(), post);
		}
	}

	@Override
	@Transactional
	public void updatePost(PostType postType, Long postId, BasePostRequestDto basePostRequestDto) {
		Post post = postService.getPostByPostTypeAndId(postType, postId);

		// Notice 말고, 다른 PostType이 생긴다면 타입에 따라 수정 검증을 해야 함.

		post.setTitle(basePostRequestDto.getPostTitle());
		post.setContent(basePostRequestDto.getPostContent());

		if (basePostRequestDto.getPostImageNames() != null && !basePostRequestDto.getPostImageNames().isEmpty()) {
			updatePostImages(basePostRequestDto.getPostImageNames(), post);
		}
	}

	private void updatePostImages(List<String> incomingPostImageNames, Post post) {
		// 1. 기존에 연결된 PostImage 목록 조회
		List<PostImage> existingPostImages = postImageService.getPostImages(post);

		// 2. 기존 이미지 names 추출
		Set<String> existingImageNames = existingPostImages.stream()
			.map(PostImage::getSaveFile)
			.collect(Collectors.toSet());

		// 3. 요청으로 받은 이미지 names Set
		Set<String> incomingImageNamesSet = new HashSet<>(incomingPostImageNames);

		// 4. 삭제할 이미지 names: 기존에는 있었지만 요청에는 없는 경우
		Set<String> imageNamesToRemove = new HashSet<>(existingImageNames);
		imageNamesToRemove.removeAll(incomingImageNamesSet);

		// 5. 추가할 이미지 names: 요청에는 있지만 기존에는 없는 경우
		Set<String> imageNamesToAdd = new HashSet<>(incomingImageNamesSet);
		imageNamesToAdd.removeAll(existingImageNames);

		// 6. 이미지 삭제 처리
		if (!imageNamesToRemove.isEmpty()) {
			// 삭제할 PostImage 엔티티 조회
			List<PostImage> postImagesToRemove = postImageService.getPostImagesByNames(imageNamesToRemove);
			//PostImage 삭제
			postImageService.removePostImages(postImagesToRemove);

			// 실제 이미지 파일 삭제 및 이벤트 발행
			List<String> removeFiles = postImagesToRemove.stream()
				.map(PostImage::getSaveFile)
				.toList();

			for (String removeFile : removeFiles) { // 성능 테스트 후 필요 시 Batch 처리 고려
				applicationEventPublisher.publishEvent(new ImageRemoveEvent(uploadPath, removeFile));
			}
		}

		// 7. 이미지 추가 처리
		if (!imageNamesToAdd.isEmpty()) {
			// 각 PostImage에 게시글 연결
			List<String> imageNames = new ArrayList<>(imageNamesToAdd);
			postImageService.addPostImagesToPost(imageNames, post); // PostImage에 Post를 연결해주기
		}
	}

	private void handleViewCount(Long postId, String clientIp) {
		// 조회 기록 키 생성
		String viewRecordId = postId + ":" + clientIp;

		// Redis에 조회 기록이 있는지 확인
		boolean hasViewed = viewRecordService.existsById(viewRecordId);
		if (!hasViewed) {
			// 조회수가 증가해야 하는 경우
			postService.incrementPostViews(postId);

			// Redis에 조회 기록 저장 (3시간 후 만료)
			viewRecordService.registerViewRecord(viewRecordId);
		}
	}
}
