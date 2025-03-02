package com.haedal.haedalweb.domain.comment.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.haedal.haedalweb.domain.comment.model.Comment;

public interface CommentRepository {
	Page<Comment> findCommentPage(Long postId, Pageable pageable);

	Optional<Comment> findCommentWithUserAndPost(Long postId, Long commentId);

	Optional<Comment> findCommentWithUser(Long commentId);

	void deleteCommentsByPostId(Long postId);

	void save(Comment comment);
}
