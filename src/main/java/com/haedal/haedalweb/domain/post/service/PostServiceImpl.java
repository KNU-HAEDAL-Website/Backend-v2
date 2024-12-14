package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostType;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.service.S3Service;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.web.post.dto.CreatePostRequestDto;
import com.haedal.haedalweb.web.post.dto.PostResponseDto;
import com.haedal.haedalweb.web.post.dto.PostSliderResponseDto;
import com.haedal.haedalweb.web.post.dto.PostSummaryResponseDto;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.domain.board.repository.BoardRepository;
import com.haedal.haedalweb.domain.post.repository.PostRepository;
import com.haedal.haedalweb.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final UserService userService;
    private final S3Service s3Service;

    @Transactional
    public void createPost(Long boardId, CreatePostRequestDto createPostRequestDTO) { // createPost 리팩토링 해야함. // 게시판 참여자만 게시글을 쓸 수 있게 해야하나?
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));
        PostType postType;

        try {
            postType = PostType.valueOf(createPostRequestDTO.getPostType());
            if (postType != PostType.ACTIVITY) throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST_TYPE);
        }

        LocalDate activityStartDate = null;
        LocalDate activityEndDate = null;

        try {
            activityStartDate = LocalDate.parse(createPostRequestDTO.getPostActivityStartDate(), DateTimeFormatter.ISO_DATE);
        } catch (DateTimeException e) {
            throw new BusinessException(ErrorCode.INVALID_ARGUMENT);
        }

        if (createPostRequestDTO.getPostActivityEndDate() != null) {
            activityEndDate = LocalDate.parse(createPostRequestDTO.getPostActivityEndDate(), DateTimeFormatter.ISO_DATE);
        }

        LocalDateTime createDate = LocalDateTime.now();
        User creator = userService.getLoggedInUser();

        Post post = Post.builder()
                .title(createPostRequestDTO.getPostTitle())
                .content(createPostRequestDTO.getPostContent())
                .imageUrl(createPostRequestDTO.getPostImageUrl()) // 이미지 생성 Controller 만들기
                .views(0L)
                .postType(postType)
                .activityStartDate(activityStartDate)
                .activityEndDate(activityEndDate)
                .createDate(createDate)
                .user(creator)
                .board(board)
                .build();

        postRepository.save(post);
    }

    @Transactional
    public void createPost(CreatePostRequestDto createPostRequestDTO) {
        PostType postType;

        try {
            postType = PostType.valueOf(createPostRequestDTO.getPostType());
            if (postType != PostType.NOTICE && postType != PostType.EVENT)
                throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST_TYPE);
        }

        LocalDate activityStartDate = null;
        LocalDate activityEndDate = null;
        LocalDateTime createDate = LocalDateTime.now();
        User creator = userService.getLoggedInUser();

        if (postType == PostType.EVENT) {
            try {
                activityStartDate = LocalDate.parse(createPostRequestDTO.getPostActivityStartDate(), DateTimeFormatter.ISO_DATE);
            } catch (DateTimeException e) {
                throw new BusinessException(ErrorCode.INVALID_ARGUMENT);
            }

            if (createPostRequestDTO.getPostActivityEndDate() != null) {
                activityEndDate = LocalDate.parse(createPostRequestDTO.getPostActivityEndDate(), DateTimeFormatter.ISO_DATE);
            }
        }

        Post post = Post.builder()
                .title(createPostRequestDTO.getPostTitle())
                .content(createPostRequestDTO.getPostContent())
                .imageUrl(createPostRequestDTO.getPostImageUrl()) // 이미지 생성 Controller 만들기
                .views(0L)
                .postType(postType)
                .activityStartDate(activityStartDate)
                .activityEndDate(activityEndDate)
                .createDate(createDate)
                .user(creator)
                .build();

        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long boardId, Long postId) { // 활동 게시글 삭제 method
        Post post = postRepository.findByBoardIdAndId(boardId, postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST_ID));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));

        User loggedInUser = userService.getLoggedInUser();
        User postCreator = post.getUser();
        User boardCreator = board.getUser();

        validateAuthorityOfPostManagement(loggedInUser, postCreator, boardCreator);

        s3Service.deleteObject(post.getImageUrl());
        postRepository.delete(post);
    }

    @Transactional
    public void deletePost(Long postId) { // 이벤트, 공지사항 삭제
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST_ID));

        try {
            if (post.getPostType() != PostType.NOTICE && post.getPostType() != PostType.EVENT)
                throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST_TYPE);
        }

        s3Service.deleteObject(post.getImageUrl());
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public Page<PostSummaryResponseDto> getPosts(Long boardId, Pageable pageable) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));
        Page<Post> postPage = postRepository.findPostsByBoard(board, pageable);

        return postPage.map(post -> convertToPostSummaryDTO(post, board));
    }

    @Transactional(readOnly = true)
    public Page<PostSummaryResponseDto> getPosts(String pType, Pageable pageable) {
        PostType postType;

        try {
            postType = PostType.valueOf(pType.toUpperCase());
            if (postType != PostType.NOTICE && postType != PostType.EVENT)
                throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST_TYPE);
        }

        Page<Post> postPage = postRepository.findPostsByPostType(postType, pageable);

        return postPage.map(post -> convertToPostSummaryDTO(post));
    }

    @Transactional
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST_ID));

        post.setViews(post.getViews()+1);
        postRepository.save(post);

        PostResponseDto postResponseDto;
        if (post.getPostType() == PostType.ACTIVITY) {
            postResponseDto = PostResponseDto.builder()
                    .postId(post.getId())
                    .postTitle(post.getTitle())
                    .postContent(post.getContent())
                    .postImageUrl(s3Service.generatePreSignedGetUrl(post.getImageUrl()))
                    .postViews(post.getViews())
                    .postCreateDate(post.getCreateDate())
                    .postActivityStartDate(post.getActivityStartDate())
                    .postActivityEndDate(post.getActivityEndDate())
                    .userId(post.getUser().getId())
                    .userName(post.getUser().getName())
                    .boardId(post.getBoard().getId())
                    .boardName(post.getBoard().getName())
                    .build();

            return postResponseDto;
        }

        postResponseDto = PostResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .postImageUrl(s3Service.generatePreSignedGetUrl(post.getImageUrl()))
                .postViews(post.getViews())
                .postCreateDate(post.getCreateDate())
                .postActivityStartDate(post.getActivityStartDate())
                .postActivityEndDate(post.getActivityEndDate())
                .userId(post.getUser().getId())
                .userName(post.getUser().getName())
                .build();

        return postResponseDto;
    }

    @Transactional(readOnly = true)
    public Page<PostSliderResponseDto> getSliderPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findPostsByPostType(PostType.EVENT, pageable);

        return postPage.map((post) -> convertToPostSliderDTO(post));
    }

    private void validateAuthorityOfPostManagement(User loggedInUser, User postCreator, User boardCreator) {
        String loggedInUserId = loggedInUser.getId();
        String postCreatorId = postCreator.getId();
        String boardCreatorId = boardCreator.getId();

        if (!postCreatorId.equals(loggedInUserId)
                && !boardCreatorId.equals(loggedInUserId)
                && loggedInUser.getRole() != Role.ROLE_ADMIN
                && loggedInUser.getRole() != Role.ROLE_WEB_MASTER) {
            throw new BusinessException(ErrorCode.FORBIDDEN_UPDATE);
        }
    }

    private PostSummaryResponseDto convertToPostSummaryDTO(Post post, Board board) {
        return PostSummaryResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postViews(post.getViews())
                .postActivityStartDate(post.getActivityStartDate())
                .postActivityEndDate(post.getActivityEndDate())
                .postCreateDate(post.getCreateDate())
                .userId(post.getUser().getId())
                .userName(post.getUser().getName())
                .boardId(board.getId())
                .boardName(board.getName())
                .build();
    }

    private PostSummaryResponseDto convertToPostSummaryDTO(Post post) {
        PostSummaryResponseDto postSummaryResponseDto = PostSummaryResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postViews(post.getViews())
                .postActivityStartDate(post.getActivityStartDate())
                .postActivityEndDate(post.getActivityEndDate())
                .postCreateDate(post.getCreateDate())
                .userId(post.getUser().getId())
                .userName(post.getUser().getName())
                .build();

        return postSummaryResponseDto;
    }

    private PostSliderResponseDto convertToPostSliderDTO(Post post) {
        PostSliderResponseDto postSliderResponseDto = PostSliderResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postImageUrl(s3Service.generatePreSignedGetUrl(post.getImageUrl()))
                .build();

        return postSliderResponseDto;
    }
}
