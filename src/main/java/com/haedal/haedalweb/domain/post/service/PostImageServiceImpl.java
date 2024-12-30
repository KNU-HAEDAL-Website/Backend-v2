package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.domain.post.model.PostImage;
import com.haedal.haedalweb.domain.post.repository.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostImageServiceImpl implements PostImageService {
    private final PostImageRepository postImageRepository;

    @Override
    public PostImage registerPostImage(PostImage postImage) {
        return postImageRepository.save(postImage);
    }
}
