package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostImage;
import java.util.List;

public interface PostImageService {
    PostImage registerPostImage(PostImage postImage);
    void registerPostImages(List<PostImage> postImages);
    List<PostImage> getPostImages(List<Long> postImageIds);
    void addPostImagesToPost(List<Long> postImageIds, Post post);
}
