package com.haedal.haedalweb.domain.comment.repository;

import com.haedal.haedalweb.domain.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(
            value = "SELECT c FROM Comment c " +
                    "JOIN FETCH c.user " +
                    "WHERE c.post.id = :postId AND c.parent IS NULL",
            countQuery = "SELECT count(c) FROM Comment c WHERE c.post.id = :postId AND c.parent IS NULL"
    )
    Page<Comment> findCommentPageId(@Param("postId") Long postId, Pageable pageable);
}
