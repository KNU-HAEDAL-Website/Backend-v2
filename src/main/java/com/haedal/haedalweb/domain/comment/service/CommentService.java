package com.haedal.haedalweb.domain.comment.service;

import com.haedal.haedalweb.domain.comment.model.Comment;
import com.haedal.haedalweb.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    void registerComment(Comment comment);

    Page<Comment> getCommentPage(Long postId, Pageable pageable);

    Comment getCommentWithUser(Long commentId);

    Comment getCommentWithUserAndPost(Long postId, Long commentId);

    void validateRemovePermission(User loggedInUser, User postCreator, User commentCreator);

    void validateUpdatePermission(User loggedInUser, User commentCreator);
}
