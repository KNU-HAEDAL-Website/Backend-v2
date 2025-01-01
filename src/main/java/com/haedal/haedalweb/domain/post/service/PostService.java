package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.application.post.dto.PostWithBoardSummaryResponseDto;
import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    void registerPost(Post post);
    void removePost(Post post);

    Post getPost(Long postId);

    Post getPostWithUserAndBoard(Long boardId, Long postId);

    void validateAuthorityOfBoardPostManagement(User loggedInUser, User postCreator, User boardCreator);
    Page<Post> getPostPage(Long boardId, Pageable pageable);
}
