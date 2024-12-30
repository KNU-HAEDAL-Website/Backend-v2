package com.haedal.haedalweb.application.post.mapper;

import com.haedal.haedalweb.application.post.dto.PostImageResponseDto;

public class PostImageMapper {
    private PostImageMapper() {
    }

    public static PostImageResponseDto toDto(String imageUrl) {
        return PostImageResponseDto.builder()
                .postImageUrl(imageUrl)
                .build();
    }
}
