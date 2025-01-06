package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostType;
import com.haedal.haedalweb.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    void registerPost(Post post);
    void removePost(Post post);

    Post getPostWithUserAndBoard(Long boardId, Long postId);

    void incrementPostViews(Long postId);

    void validateAuthorityOfBoardPostManagement(User loggedInUser, User postCreator, User boardCreator);

    Post getPost(Long postId);

    Page<Post> getPostPage(Long boardId, Pageable pageable);
    Page<Post> getPostPage(PostType postType, Pageable pageable);

    Post getPostByPostTypeAndId(PostType postType, Long postId);
}
