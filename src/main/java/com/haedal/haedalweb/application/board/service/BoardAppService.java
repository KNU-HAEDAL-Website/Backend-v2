package com.haedal.haedalweb.application.board.service;

import com.haedal.haedalweb.application.board.dto.BoardResponseDto;
import com.haedal.haedalweb.application.board.dto.CreateBoardRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BoardAppService {
    void registerBoard(Long activityId, MultipartFile boardImageFile, CreateBoardRequestDto createBoardRequestDto);

    BoardResponseDto getBoard(Long activityId, Long boardId);

    Page<BoardResponseDto> getBoardPage(Long activityId, Pageable pageable);
}
