package com.haedal.haedalweb.infrastructure.post.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostType;
import com.haedal.haedalweb.domain.post.repository.PostRepository;

@Repository
public class PostRepositoryAdapter implements PostRepository {

	private final PostJpaRepository postJpaRepository;

	public PostRepositoryAdapter(PostJpaRepository postJpaRepository) {
		this.postJpaRepository = postJpaRepository;
	}

	@Override
	public void save(Post post) {
		postJpaRepository.save(post);
	}

	@Override
	public void delete(Post post) {
		postJpaRepository.delete(post);
	}

	@Override
	public Optional<Post> findById(Long postId) {
		return postJpaRepository.findById(postId);
	}

	@Override
	public Optional<Post> findByPostTypeAndId(PostType postType, Long postId) {
		return postJpaRepository.findByPostTypeAndId(postType, postId);
	}

	@Override
	public Optional<Post> findPostWithUserAndBoard(Long boardId, Long postId) {
		return postJpaRepository.findPostWithUserAndBoard(boardId, postId);
	}

	@Override
	public Page<Post> findPostPageByBoardId(Long boardId, Pageable pageable) {
		return postJpaRepository.findPostPageByBoardId(boardId, pageable);
	}

	@Override
	public Page<Post> findPostPageByPostType(PostType postType, Pageable pageable) {
		return postJpaRepository.findPostPageByPostType(postType, pageable);
	}

	@Override
	public int incrementViewCount(Long postId) {
		return postJpaRepository.incrementViewCount(postId);
	}
}
