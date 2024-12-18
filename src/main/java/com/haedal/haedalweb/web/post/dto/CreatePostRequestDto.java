package com.haedal.haedalweb.web.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePostRequestDto {
    @Schema(description = "게시글 이름", example = "게시글1")
    @Size(min = 1, max = 50, message = "게시글 이름은 1자 이상 50자 이하여야 합니다.")
    private String postTitle;

    @Size(min = 1, max = 200000, message = "게시글의 메타 정보와 내용은 합쳐서 20만자 이하여야 합니다.")
    private String postContent;

    @Schema(description = "활동 시작일 (이벤트와 활동은 필수, 공지사항은 생략)", example = "yyyy-MM-dd (2024-07-24)")
    private String postActivityStartDate;

    @Schema(description = "활동 종료일 (생략 가능)", example = "yyyy-MM-dd (2024-07-24)")
    private String postActivityEndDate;

    @Schema(description = "게시글 타입", example = "(ACTIVITY, NOTICE)")
    private String postType;
}
