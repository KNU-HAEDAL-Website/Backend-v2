package com.haedal.haedalweb.domain.post.service;

import java.util.List;
import java.util.Set;

import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostImage;

public interface PostImageService {
	PostImage registerPostImage(PostImage postImage);

	void registerPostImages(List<PostImage> postImages);

	void removePostImages(List<PostImage> postImages);

	List<PostImage> getPostImagesByNames(List<String> postImageNames);

	List<PostImage> getPostImagesByNames(Set<String> postImageNames);

	List<PostImage> getPostImages(Post post);

	void addPostImagesToPost(List<String> postImageNames, Post post);
}
