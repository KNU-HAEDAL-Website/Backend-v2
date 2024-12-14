package com.haedal.haedalweb.domain.post.repository;

import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByBoardId(Long boardId);

    Page<Post> findPostsByPostType(PostType postType, Pageable pageable);

    Page<Post> findPostsByBoard(Board board, Pageable pageable);

    Optional<Post> findByBoardIdAndId(Long boardId, Long postId);
}
