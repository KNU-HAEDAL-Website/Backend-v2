package com.haedal.haedalweb.application.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.haedal.haedalweb.application.board.dto.BoardRequestDto;
import com.haedal.haedalweb.application.board.dto.BoardResponseDto;

public interface BoardAppService {
	void registerBoard(Long activityId, MultipartFile boardImageFile, BoardRequestDto boardRequestDto);

	BoardResponseDto getBoard(Long activityId, Long boardId);

	Page<BoardResponseDto> getBoardPage(Long activityId, Pageable pageable);

	void updateBoardImage(Long activityId, Long boardId, MultipartFile boardImageFile);

	void updateBoard(Long activityId, Long boardId, BoardRequestDto boardRequestDto);

	void removeBoard(Long activityId, Long boardId);
}
