package com.haedal.haedalweb.application.post.service;

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

        List<Long> postImageIds = postWithBoardRequestDto.getPostImageIds();

        if (postImageIds != null && !postImageIds.isEmpty()) { // 게시글에 이미지가 포함되어 있다면
            List<PostImage> postImages = postImageService.getPostImages(postImageIds); // 이미지 ID들을 기반으로 PostImage 엔티티 조회
            postImageService.validatePostImages(postImageIds, postImages); // 이미지 검증
            postImages.forEach(postImage -> postImage.setPost(post)); // Post와 PostImage 연결
        }
    }

    @Override
    @Transactional
    public PostImageResponseDto registerPostImage(MultipartFile postImageFile) {
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

}
