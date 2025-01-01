package com.haedal.haedalweb.application.post.mapper;

import com.haedal.haedalweb.application.post.dto.PostImageResponseDto;
import com.haedal.haedalweb.domain.post.model.PostImage;

public class PostImageMapper {
    private PostImageMapper() {
    }

    public static PostImageResponseDto toDto(String imageUrl, PostImage postImage) {
        return PostImageResponseDto.builder()
                .postImageId(postImage.getId())
                .postImageUrl(imageUrl)
                .build();
    }
}
