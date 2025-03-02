package com.haedal.haedalweb.infrastructure.comment.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.comment.model.Comment;
import com.haedal.haedalweb.domain.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryAdapter implements CommentRepository {

	private final CommentJpaRepository commentJpaRepository;

	@Override
	public Page<Comment> findCommentPage(Long postId, Pageable pageable) {
		return commentJpaRepository.findCommentPageByPostId(postId, pageable);
	}

	@Override
	public Optional<Comment> findCommentWithUserAndPost(Long postId, Long commentId) {
		return commentJpaRepository.findCommentWithUserAndPost(postId, commentId);
	}

	@Override
	public Optional<Comment> findCommentWithUser(Long commentId) {
		return commentJpaRepository.findCommentWithUser(commentId);
	}

	@Override
	public void deleteCommentsByPostId(Long postId) {
		commentJpaRepository.deleteCommentsByPostId(postId);
	}

	@Override
	public void save(Comment comment) {
		commentJpaRepository.save(comment);
	}
}
