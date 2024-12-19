package com.haedal.haedalweb.domain.board.service;

import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.web.board.dto.UpdateBoardRequestDto;
import com.haedal.haedalweb.application.board.dto.BoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    void registerBoard(List<User> participants, Board board);

    Board getBoard(Long activityId, Long boardId);

    //        void createBoard(Long activityId, CreateBoardRequestDto createBoardRequestDto);
    Page<BoardResponseDto> getBoardDTOs(Long activityId, Pageable pageable);
    BoardResponseDto getBoardDTO(Long activityId, Long boardId);
    void deleteBoard(Long activityId, Long boardId);
    void updateBoardImage(Long activityId, Long boardId, String newImageUrl);
    void updateBoard(Long activityId, Long boardId, UpdateBoardRequestDto updateBoardRequestDto);
    boolean hasBoardsByActivityId(Long activityId);
    void validateParticipants(List<User> participants, List<String> participantIds);
}
