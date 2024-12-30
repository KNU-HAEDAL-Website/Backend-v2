package com.haedal.haedalweb.application.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImageResponseDto {
    @Schema(description = "게시글 이미지 파일 Url")
    private String postImageUrl;
}
