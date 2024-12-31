package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.post.model.PostImage;
import com.haedal.haedalweb.domain.post.repository.PostImageRepository;
import com.haedal.haedalweb.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<PostImage> getPostImages(List<Long> postImageIds) {
       return postImageRepository.findAllById(postImageIds);
    }

    @Override
    public void validatePostImages(List<Long> postImageIds, List<PostImage> postImages) {
        // 모든 이미지가 존재하는지 확인
        if (postImages.size() != postImageIds.size()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_POST_IMAGE);
        }
    }
}
