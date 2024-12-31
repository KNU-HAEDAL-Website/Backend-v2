package com.haedal.haedalweb.domain.board.service;

import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    void registerBoard(Board board);
    Board getBoard(Long boardId);
    Board getBoardWithParticipants(Long activityId, Long boardId);
    Page<Board> getBoardPage(Long activityId, Pageable pageable);
    Board getBoardWithUser(Long activityId, Long boardId);
    Board getBoardWithUserAndParticipants(Long activityId, Long boardId);
    void removeBoard(Board board);
    boolean hasBoardsByActivityId(Long activityId);
    void addParticipantsToBoard(List<User> participants, Board board);
    void validateAuthorityOfBoardManagement(User loggedInUser, User creator);
}
