package com.haedal.haedalweb.application.post.dto;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostWithBoardSummaryResponseDto extends BasePostSummaryResponseDto{
    @Schema(description = "게시판 id")
    private Long boardId;

    @Schema(description = "활동 시작일")
    private LocalDate postActivityStartDate;

    @Schema(description = "활동 종료일")
    private LocalDate postActivityEndDate;
}
