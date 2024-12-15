package com.haedal.haedalweb.domain.board.service;

import com.haedal.haedalweb.web.board.dto.CreateBoardRequestDto;
import com.haedal.haedalweb.web.board.dto.UpdateBoardRequestDto;
import com.haedal.haedalweb.web.board.dto.BoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    void createBoard(Long activityId, CreateBoardRequestDto createBoardRequestDto);
    Page<BoardResponseDto> getBoardDTOs(Long activityId, Pageable pageable);
    BoardResponseDto getBoardDTO(Long activityId, Long boardId);
    void deleteBoard(Long activityId, Long boardId);
    void updateBoardImage(Long activityId, Long boardId, String newImageUrl);
    void updateBoard(Long activityId, Long boardId, UpdateBoardRequestDto updateBoardRequestDto);
    boolean existsByActivityId(Long activityId);
}
