package com.haedal.haedalweb.application.board.mapper;

import com.haedal.haedalweb.application.board.dto.BoardImageResponseDto;
import com.haedal.haedalweb.domain.board.model.BoardImage;

public class BoardImageMapper {
    private BoardImageMapper() {
    }

    public static BoardImageResponseDto toDto(BoardImage boardImage) {
        return BoardImageResponseDto.builder()
                .boardImageId(boardImage.getId())
                .boardImageOriginalFile(boardImage.getOriginalFile())
                .boardImageSaveFile(boardImage.getSaveFile())
                .build();
    }
}
