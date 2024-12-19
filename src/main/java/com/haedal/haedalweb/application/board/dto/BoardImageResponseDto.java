package com.haedal.haedalweb.application.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardImageResponseDto {
    @Schema(description = "board 이미지 id")
    private Long boardImageId;

    @Schema(description = "board 이미지 원본 이름")
    private String boardImageOriginalFile;


    @Schema(description = "board 이미지 저장 이름")
    private String boardImageSaveFile;
}
