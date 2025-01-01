package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostImage;
import java.util.List;
import java.util.Set;

public interface PostImageService {
    PostImage registerPostImage(PostImage postImage);
    void registerPostImages(List<PostImage> postImages);

    void removePostImages(List<PostImage> postImages);

    List<PostImage> getPostImagesByIds(List<Long> postImageIds);

    List<PostImage> getPostImagesByIds(Set<Long> postImageIds);

    List<PostImage> getPostImages(Post post);
    void addPostImagesToPost(List<Long> postImageIds, Post post);
}
