package com.haedal.haedalweb.domain.board.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.board.model.Participant;
import com.haedal.haedalweb.domain.board.repository.BoardRepository;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	private final BoardRepository boardRepository;

	@Override
	public void registerBoard(Board board) {
		boardRepository.save(board);
	}

	@Override
	public Board getBoard(Long boardId) {
		return boardRepository.findBoard(boardId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));
	}

	@Override
	public Board getBoardWithParticipants(Long activityId, Long boardId) {
		return boardRepository.findBoardWithParticipants(activityId, boardId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));
	}

	@Override
	public Page<Board> getBoardPage(Long activityId, Pageable pageable) {
		return boardRepository.findBoardPage(activityId, pageable);
	}

	@Override
	public Board getBoardWithUser(Long activityId, Long boardId) {
		return boardRepository.findBoardWithUser(activityId, boardId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));
	}

	@Override
	public Board getBoardWithUserAndParticipants(Long activityId, Long boardId) {
		return boardRepository.findBoardWithUserAndParticipants(activityId, boardId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));
	}

	@Override
	public void removeBoard(Board board) {
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
}
