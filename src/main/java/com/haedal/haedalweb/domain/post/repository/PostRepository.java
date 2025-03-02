package com.haedal.haedalweb.domain.post.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostType;

public interface PostRepository {
	void save(Post post);

	void delete(Post post);

	Optional<Post> findById(Long postId);

	Optional<Post> findByPostTypeAndId(PostType postType, Long postId);

	Optional<Post> findPostWithUserAndBoard(Long boardId, Long postId);

	Page<Post> findPostPageByBoardId(Long boardId, Pageable pageable);

	Page<Post> findPostPageByPostType(PostType postType, Pageable pageable);

	int incrementViewCount(Long postId);
}
