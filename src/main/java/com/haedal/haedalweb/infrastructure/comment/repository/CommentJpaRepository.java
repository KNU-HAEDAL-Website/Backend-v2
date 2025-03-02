package com.haedal.haedalweb.infrastructure.comment.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.haedal.haedalweb.domain.comment.model.Comment;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
	@Query(
		value = "SELECT c FROM Comment c "
			+ "JOIN FETCH c.user "
			+ "WHERE c.post.id = :postId AND c.parent IS NULL",
		countQuery = "SELECT count(c) FROM Comment c WHERE c.post.id = :postId AND c.parent IS NULL"
	)
	Page<Comment> findCommentPageByPostId(@Param("postId") Long postId, Pageable pageable);

	@Query(
		"SELECT c FROM Comment c "
			+ "JOIN FETCH c.user "
			+ "JOIN FETCH c.post "
			+ "WHERE c.post.id = :postId AND c.id = :commentId AND c.deleted = FALSE"
	)
	Optional<Comment> findCommentWithUserAndPost(@Param("postId") Long postId, @Param("commentId") Long commentId);

	@Query(
		"SELECT c FROM Comment c "
			+ "JOIN FETCH c.user "
			+ "WHERE c.id = :commentId AND c.deleted = FALSE"
	)
	Optional<Comment> findCommentWithUser(@Param("commentId") Long commentId);

	@Modifying(clearAutomatically = true)
	@Query(
		"DELETE FROM Comment c "
			+ "WHERE c.post.id = :postId"
	)
	void deleteCommentsByPostId(@Param("postId") Long postId);
}
