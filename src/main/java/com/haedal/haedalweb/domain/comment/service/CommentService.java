package com.haedal.haedalweb.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.haedal.haedalweb.domain.comment.model.Comment;
import com.haedal.haedalweb.domain.user.model.User;

public interface CommentService {
	void registerComment(Comment comment);

	void removeComments(Long postId);

	Page<Comment> getCommentPage(Long postId, Pageable pageable);

	Comment getCommentWithUser(Long commentId);

	Comment getCommentWithUserAndPost(Long postId, Long commentId);

	void validateRemovePermission(User loggedInUser, User postCreator, User commentCreator);

	void validateUpdatePermission(User loggedInUser, User commentCreator);
}
