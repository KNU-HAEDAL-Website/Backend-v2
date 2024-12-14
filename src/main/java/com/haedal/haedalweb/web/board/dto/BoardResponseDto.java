package com.haedal.haedalweb.web.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.net.URL;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDto {
    @Schema(description = "게시판 id")
    private Long boardId;

    @Schema(description = "게시판 이름")
    private String boardName;

    @Schema(description = "게시판 소개")
    private String boardIntro;

    @Schema(description = "게시판 대표 이미지 파일 Url")
    private String boardImageUrl;

    @Schema(description = "참여 인원 목록")
    private List<ParticipantResponseDto> participants;

    @Schema(description = "활동 id")
    private Long activityId;
}
