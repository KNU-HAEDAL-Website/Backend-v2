package com.haedal.haedalweb.application.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasePostRequestDto {
    @Schema(description = "게시글 이름", example = "게시글1")
    @Size(min = 1, max = 50, message = "게시글 이름은 1자 이상 50자 이하여야 합니다.")
    private String postTitle;

    @Size(min = 1, max = 200000, message = "게시글의 메타 정보와 내용은 합쳐서 20만자 이하여야 합니다.")
    private String postContent;

    @Schema(description = "게시글 이미지 ID", example = "[1, 2]")
    private List<Long> postImageIds;
}
