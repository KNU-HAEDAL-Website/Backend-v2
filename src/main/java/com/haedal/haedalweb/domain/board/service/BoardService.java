package com.haedal.haedalweb.domain.board.service;

import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.web.board.dto.UpdateBoardRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    void registerBoard(List<User> participants, Board board);
    Board getBoardWithImageAndParticipants(Long activityId, Long boardId);
    Page<Board> getBoardPage(Long activityId, Pageable pageable);
    Board getBoardWithImageAndUser(Long activityId, Long boardId);

    void deleteBoard(Long activityId, Long boardId);
    void updateBoard(Long activityId, Long boardId, UpdateBoardRequestDto updateBoardRequestDto);
    boolean hasBoardsByActivityId(Long activityId);

    void validateAuthorityOfBoardManagement(User loggedInUser, User creator);
}
