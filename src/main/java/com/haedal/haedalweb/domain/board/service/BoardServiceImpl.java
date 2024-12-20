package com.haedal.haedalweb.domain.board.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.board.model.Participant;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.domain.board.repository.BoardRepository;
import com.haedal.haedalweb.domain.post.repository.PostRepository;
import com.haedal.haedalweb.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private final UserService userService;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;


    @Override
    public void registerBoard(Board board) {
        boardRepository.save(board);
    }

    @Override
    public Board getBoardWithImageAndParticipants(Long activityId, Long boardId) {
        return boardRepository.findBoardWithImageAndParticipants(activityId, boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));
    }

    @Override
    public Page<Board> getBoardPage(Long activityId, Pageable pageable) {
        return boardRepository.findBoardPage(activityId, pageable);
    }

    @Override
    public Board getBoardWithImageAndUser(Long activityId, Long boardId) {
        return boardRepository.findBoardWithImageAndUser(activityId, boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));
    }

    @Override
    public Board getBoardWithUserAndParticipants(Long activityId, Long boardId) {
        return boardRepository.findBoardWithUserAndParticipants(activityId, boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));
    }

    @Transactional
    public void deleteBoard(Long activityId, Long boardId) {
        Board board = boardRepository.findBoardWithUserAndParticipants(activityId, boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));

        User loggedInUser = userService.getLoggedInUser();
        User creator = board.getUser();

        validateAuthorityOfBoardManagement(loggedInUser, creator);
        validateDeleteBoardRequest(boardId);

        boardRepository.delete(board);
    }


    @Override
    public boolean hasBoardsByActivityId(Long activityId) {
        return boardRepository.existsByActivityId(activityId);
    }

    @Override
    public void addParticipantsToBoard(List<User> participants, Board board) {
        for (User user : participants) {
            Participant participant = Participant.builder()
                    .board(board)
                    .user(user)
                    .build();

            board.addParticipant(participant);
        }
    }

    @Override
    public void validateAuthorityOfBoardManagement(User loggedInUser, User creator) {
        if (loggedInUser.getRole() == Role.ROLE_TEAM_LEADER && !loggedInUser.getId().equals(creator.getId())) {
            throw new BusinessException(ErrorCode.FORBIDDEN_UPDATE);
        }
    }

    private void validateDeleteBoardRequest(Long boardId) {
        if (postRepository.existsByBoardId(boardId)) {
            throw new BusinessException(ErrorCode.EXIST_POST);
        }
    }
}
