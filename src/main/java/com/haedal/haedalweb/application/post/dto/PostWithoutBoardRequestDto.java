package com.haedal.haedalweb.application.post.dto;

import com.haedal.haedalweb.domain.post.model.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostWithoutBoardRequestDto {
    @Schema(description = "게시글 타입", example = "(ACTIVITY, NOTICE)")
    @NotNull(message = "게시글 타입은 필수 항목입니다.")
    private PostType postType;
}
