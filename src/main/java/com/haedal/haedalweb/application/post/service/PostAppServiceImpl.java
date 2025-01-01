package com.haedal.haedalweb.application.post.service;

import com.haedal.haedalweb.application.post.dto.BasePostRequestDto;
import com.haedal.haedalweb.application.post.dto.PostImageResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardRequestDto;
import com.haedal.haedalweb.application.post.mapper.PostImageMapper;
import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.board.service.BoardService;
import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostImage;
import com.haedal.haedalweb.domain.post.model.PostType;
import com.haedal.haedalweb.domain.post.service.PostImageService;
import com.haedal.haedalweb.domain.post.service.PostService;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.infrastructure.image.ImageRemoveEvent;
import com.haedal.haedalweb.infrastructure.image.ImageSaveRollbackEvent;
import com.haedal.haedalweb.infrastructure.image.ImageUtil;
import com.haedal.haedalweb.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

@Service
public class PostAppServiceImpl implements PostAppService {
    private final PostImageService postImageService;
    private final PostService postService;
    private final BoardService boardService;
    private final SecurityService securityService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final String uploadPath;
    private final String uploadUrl;

    public PostAppServiceImpl(PostImageService postImageService, PostService postService, BoardService boardService, SecurityService securityService, ApplicationEventPublisher applicationEventPublisher, @Value("${file.path.upload-post-images}") String uploadPath, @Value("${file.url.upload-post-images}") String uploadUrl) {
        this.postImageService = postImageService;
        this.postService = postService;
        this.boardService = boardService;
        this.securityService = securityService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.uploadPath = uploadPath;
        this.uploadUrl = uploadUrl;
    }
    @Override
    @Transactional
    public void registerPost(Long boardId, PostWithBoardRequestDto postWithBoardRequestDto) { // Board가 존재하는 Post 생성 (활동 게시판의 게시글)
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

        List<Long> postImageIds = postWithBoardRequestDto.getPostImageIds(); // Post와 연결할 PostImage의 id 조회
        postImageService.addPostImagesToPost(postImageIds, post); // PostImage에 Post를 연결해주기
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
        List<Long> postImageIds = basePostRequestDto.getPostImageIds(); // Post와 연결할 PostImage의 id 조회
        postImageService.addPostImagesToPost(postImageIds, post); // PostImage에 Post를 연결해주기
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
        Post post = postService.getPost(postId);

        // Notice 말고, 다른 PostType이 생긴다면 삭제 검증을 해야 함.

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
}
