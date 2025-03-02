package com.haedal.haedalweb.domain.post.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostImage;

public interface PostImageRepository {
	PostImage save(PostImage postImage);

	List<PostImage> saveAll(List<PostImage> postImages);

	void deleteAll(List<PostImage> postImages);

	List<PostImage> findByPost(Post post);

	List<PostImage> findAllBySaveFileIn(List<String> postImageNames);

	List<PostImage> findAllBySaveFileIn(Set<String> postImageNames);

	List<PostImage> findOldImages(LocalDateTime threshold);
}
