package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostType;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public void registerPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void removePost(Post post) {
        postRepository.delete(post);
    }

    @Override
    public Post getPostByPostTypeAndId(PostType postType, Long postId) {
        return postRepository.findByPostTypeAndId(postType, postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST_ID));
    }

    @Override
    public Post getPostWithUserAndBoard(Long boardId, Long postId) {
        return postRepository.findPostWithUserAndBoard(boardId, postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST_ID));
    }

    @Override
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST_ID));
    }

    @Override
    public Page<Post> getPostPage(Long boardId, Pageable pageable) {
        return postRepository.findPostPageByBoardId(boardId, pageable);
    }

    @Override
    public Page<Post> getPostPage(PostType postType, Pageable pageable) {
        return postRepository.findPostPageByPostType(postType, pageable);
    }

    @Override
    public void incrementPostViews(Long postId) {
        int updatedRows = postRepository.incrementViewCount(postId);

        if (updatedRows == 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST_ID);
        }
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
