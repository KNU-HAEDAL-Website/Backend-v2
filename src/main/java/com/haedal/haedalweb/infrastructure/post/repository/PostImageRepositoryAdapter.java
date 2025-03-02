package com.haedal.haedalweb.infrastructure.post.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostImage;
import com.haedal.haedalweb.domain.post.repository.PostImageRepository;

@Repository
public class PostImageRepositoryAdapter implements PostImageRepository {

	private final PostImageJpaRepository postImageJpaRepository;

	public PostImageRepositoryAdapter(PostImageJpaRepository postImageJpaRepository) {
		this.postImageJpaRepository = postImageJpaRepository;
	}

	@Override
	public PostImage save(PostImage postImage) {
		return postImageJpaRepository.save(postImage);
	}

	@Override
	public List<PostImage> saveAll(List<PostImage> postImages) {
		return postImageJpaRepository.saveAll(postImages);
	}

	@Override
	public void deleteAll(List<PostImage> postImages) {
		postImageJpaRepository.deleteAll(postImages);
	}

	@Override
	public List<PostImage> findByPost(Post post) {
		return postImageJpaRepository.findByPost(post);
	}

	@Override
	public List<PostImage> findAllBySaveFileIn(List<String> postImageNames) {
		return postImageJpaRepository.findAllBySaveFileIn(postImageNames);
	}

	@Override
	public List<PostImage> findAllBySaveFileIn(Set<String> postImageNames) {
		return postImageJpaRepository.findAllBySaveFileIn(postImageNames);
	}

	@Override
	public List<PostImage> findOldImages(LocalDateTime threshold) {
		return postImageJpaRepository.findOldImages(threshold);
	}
}
