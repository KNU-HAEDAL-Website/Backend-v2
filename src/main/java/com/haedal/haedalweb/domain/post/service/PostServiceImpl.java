package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.security.service.SecurityService;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.domain.board.repository.BoardRepository;
import com.haedal.haedalweb.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final SecurityService securityService;

    @Override
    public void registerPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void removePost(Post post) {
        postRepository.delete(post);
    }

    @Override
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST_ID));
    }

    @Transactional
    public void deletePost(Long boardId, Long postId) { // 활동 게시글 삭제 method
        Post post = postRepository.findByBoardIdAndId(boardId, postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST_ID));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));

        User loggedInUser = securityService.getLoggedInUser();
        User postCreator = post.getUser();
        User boardCreator = board.getUser();

        validateAuthorityOfBoardPostManagement(loggedInUser, postCreator, boardCreator);

        postRepository.delete(post);
    }

//    @Transactional
//    public void deletePost(Long postId) { // 이벤트, 공지사항 삭제
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST_ID));
//
//        try {
//            if (post.getPostType() != PostType.NOTICE && post.getPostType() != PostType.EVENT)
//                throw new IllegalArgumentException();
//        } catch (IllegalArgumentException e) {
//            throw new BusinessException(ErrorCode.BAD_REQUEST_POST_TYPE);
//        }
//
//        postRepository.delete(post);
//    }

//    @Transactional(readOnly = true)
//    public Page<PostWithBoardSummaryResponseDto> getPosts(Long boardId, Pageable pageable) {
//        Board board = boardRepository.findById(boardId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));
//        Page<Post> postPage = postRepository.findPostsByBoard(board, pageable);
//
//        return postPage.map(post -> convertToPostSummaryDTO(post, board));
//    }

//    @Transactional(readOnly = true)
//    public Page<PostWithBoardSummaryResponseDto> getPosts(String pType, Pageable pageable) {
//        PostType postType;
//
//        try {
//            postType = PostType.valueOf(pType.toUpperCase());
//            if (postType != PostType.NOTICE && postType != PostType.EVENT)
//                throw new IllegalArgumentException();
//        } catch (IllegalArgumentException e) {
//            throw new BusinessException(ErrorCode.BAD_REQUEST_POST_TYPE);
//        }
//
//        Page<Post> postPage = postRepository.findPostsByPostType(postType, pageable);
//
//        return postPage.map(post -> convertToPostSummaryDTO(post));
//    }

//    @Transactional
//    public PostResponseDto getPost(Long postId) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST_ID));
//
//        postRepository.save(post);
//
//        PostResponseDto postResponseDto;
//        if (post.getPostType() == PostType.ACTIVITY) {
//            postResponseDto = PostResponseDto.builder()
//                    .postId(post.getId())
//                    .postTitle(post.getTitle())
//                    .postContent(post.getContent())
//                    .postViews(0L) // Views 구현해야 함.
//                    .postCreateDate(post.getRegDate())
//                    .postActivityStartDate(post.getActivityStartDate())
//                    .postActivityEndDate(post.getActivityEndDate())
//                    .userId(post.getUser().getId())
//                    .userName(post.getUser().getName())
//                    .boardId(post.getBoard().getId())
//                    .boardName(post.getBoard().getName())
//                    .build();
//
//            return postResponseDto;
//        }
//
//        postResponseDto = PostResponseDto.builder()
//                .postId(post.getId())
//                .postTitle(post.getTitle())
//                .postContent(post.getContent())
//                .postImageUrl(null) // 이미지 구현해야 함.
//                .postViews(0L) // Views 구현해야 함.
//                .postCreateDate(post.getRegDate())
//                .postActivityStartDate(post.getActivityStartDate())
//                .postActivityEndDate(post.getActivityEndDate())
//                .userId(post.getUser().getId())
//                .userName(post.getUser().getName())
//                .build();
//
//        return postResponseDto;
//    }

    @Override
    public Post getPostWithUserAndBoard(Long boardId, Long postId) {
        return postRepository.findPostWithUserAndBoard(boardId, postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST_ID));
    }
    @Override
    public Page<Post> getPostPage(Long boardId, Pageable pageable) {
        return postRepository.findPostPage(boardId, pageable);
    }

    @Override
    public void validateAuthorityOfBoardPostManagement(User loggedInUser, User postCreator, User boardCreator) {
        String loggedInUserId = loggedInUser.getId();
        String postCreatorId = postCreator.getId();
        String boardCreatorId = boardCreator.getId();

        if (loggedInUser.getRole() == Role.ROLE_WEB_MASTER || loggedInUser.getRole() == Role.ROLE_ADMIN) return; // 관리자
        if (boardCreatorId.equals(loggedInUserId) && loggedInUser.getRole() == Role.ROLE_TEAM_LEADER) return; // 자신이 만든 게시판의 글 && 현재 팀장
        if (postCreatorId.equals(loggedInUserId)) return; // 자신이 작성한 글

        throw new BusinessException(ErrorCode.FORBIDDEN_UPDATE); // 위의 경우 제외 예외 발생
    }

}
