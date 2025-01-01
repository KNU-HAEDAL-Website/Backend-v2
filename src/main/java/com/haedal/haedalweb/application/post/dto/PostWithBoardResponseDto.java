package com.haedal.haedalweb.application.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostWithBoardResponseDto extends BasePostResponseDto {

    @Schema(description = "게시판 id")
    private Long boardId;

    @Schema(description = "활동 시작일", example = "yyyy-MM-dd (2024-07-24)")
    private LocalDate postActivityStartDate;

    @Schema(description = "활동 종료일", example = "yyyy-MM-dd (2024-07-24)")
    private LocalDate postActivityEndDate;
}
