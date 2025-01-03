package com.haedal.haedalweb.domain.post.repository;

import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(
            value = "SELECT p FROM Post p " +
                    "JOIN FETCH p.user " +
                    "WHERE p.board.id = :boardId",
            countQuery = "SELECT count(p) FROM Post p WHERE p.board.id = :boardId"
    )
    Page<Post> findPostPageByBoardId(@Param("boardId") Long boardId, Pageable pageable);

    @Query(
            value = "SELECT p FROM Post p " +
                    "JOIN FETCH p.user " +
                    "WHERE p.postType = :postType",
            countQuery = "SELECT count(p) FROM Post p WHERE p.postType = :postType"
    )
    Page<Post> findPostPageByPostType(@Param("postType") PostType postType, Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "JOIN FETCH p.user " +
            "JOIN FETCH p.board " +
            "WHERE p.id = :postId AND p.board.id = :boardId")
    Optional<Post> findPostWithUserAndBoard(Long boardId, Long postId);

    @Query("SELECT p FROM Post p " +
            "JOIN FETCH p.user " +
            "WHERE p.id = :postId")
    Optional<Post> findByPostTypeAndId(PostType postType, Long postId);

    @Query("UPDATE Post p SET p.postViews = p.postViews + 1 " +
            "WHERE p.id = :postId")
    @Modifying
    int incrementViewCount(Long postId);
}
