package com.haedal.haedalweb.infrastructure.post.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostType;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

	@Query(
		value = "SELECT p FROM Post p "
			+ "JOIN FETCH p.user "
			+ "WHERE p.board.id = :boardId",
		countQuery = "SELECT count(p) FROM Post p WHERE p.board.id = :boardId"
	)
	Page<Post> findPostPageByBoardId(@Param("boardId") Long boardId, Pageable pageable);

	@Query(
		value = "SELECT p FROM Post p "
			+ "JOIN FETCH p.user "
			+ "WHERE p.postType = :postType",
		countQuery = "SELECT count(p) FROM Post p WHERE p.postType = :postType"
	)
	Page<Post> findPostPageByPostType(@Param("postType") PostType postType, Pageable pageable);

	@Query(
		"SELECT p FROM Post p "
			+ "JOIN FETCH p.user "
			+ "JOIN FETCH p.board b "
			+ "JOIN FETCH b.user "
			+ "WHERE p.id = :postId AND b.id = :boardId"
	)
	Optional<Post> findPostWithUserAndBoard(@Param("boardId") Long boardId, @Param("postId") Long postId);

	@Query(
		"SELECT p FROM Post p "
			+ "JOIN FETCH p.user "
			+ "WHERE p.id = :postId AND p.postType = :postType"
	)
	Optional<Post> findByPostTypeAndId(@Param("postType") PostType postType, @Param("postId") Long postId);

	@Modifying
	@Query(
		"UPDATE Post p SET p.postViews = p.postViews + 1 "
			+ "WHERE p.id = :postId"
	)
	int incrementViewCount(@Param("postId") Long postId);
}
