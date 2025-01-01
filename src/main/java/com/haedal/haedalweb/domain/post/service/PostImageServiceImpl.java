package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostImage;
import com.haedal.haedalweb.domain.post.repository.PostImageRepository;
import com.haedal.haedalweb.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostImageServiceImpl implements PostImageService {
    private final PostImageRepository postImageRepository;

    @Override
    public PostImage registerPostImage(PostImage postImage) {
        return postImageRepository.save(postImage);
    }

    @Override
    public void registerPostImages(List<PostImage> postImages) {
        postImageRepository.saveAll(postImages);
    }

    @Override
    public void removePostImages(List<PostImage> postImages) {
        postImageRepository.deleteAll(postImages);
    }

    @Override
    public List<PostImage> getPostImagesByIds(List<Long> postImageIds) {
       return postImageRepository.findAllById(postImageIds);
    }

    @Override
    public List<PostImage> getPostImagesByIds(Set<Long> postImageIds) {
        return postImageRepository.findAllById(postImageIds);
    }

    @Override
    public List<PostImage> getPostImages(Post post) {
        return postImageRepository.findByPost(post);
    }

    @Override
    public void addPostImagesToPost(List<Long> postImageIds, Post post) {
        if (postImageIds != null && !postImageIds.isEmpty()) { // 게시글에 이미지가 포함되어 있다면
            List<PostImage> postImages = getPostImagesByIds(postImageIds); // 이미지 ID들을 기반으로 PostImage 엔티티 조회
            validatePostImages(postImageIds, postImages); // 이미지 검증
            postImages.forEach(postImage -> postImage.setPost(post)); // Post와 PostImage 연결
        }
    }

    private void validatePostImages(List<Long> postImageIds, List<PostImage> postImages) {
        // 모든 이미지가 존재하는지 확인
        if (postImages.size() != postImageIds.size()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST_IMAGE);
        }
    }
}
