package com.haedal.haedalweb.infrastructure.board.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.board.repository.BoardRepository;

@Repository
public class BoardRepositoryAdapter implements BoardRepository {

	private final BoardJpaRepository boardJpaRepository;

	public BoardRepositoryAdapter(BoardJpaRepository boardJpaRepository) {
		this.boardJpaRepository = boardJpaRepository;
	}

	@Override
	public boolean existsByActivityId(Long activityId) {
		return boardJpaRepository.existsByActivityId(activityId);
	}

	@Override
	public Page<Board> findBoardPage(Long activityId, Pageable pageable) {
		return boardJpaRepository.findBoardPage(activityId, pageable);
	}

	@Override
	public Optional<Board> findBoard(Long boardId) {
		return boardJpaRepository.findBoard(boardId);
	}

	@Override
	public Optional<Board> findBoardWithUserAndParticipants(Long activityId, Long boardId) {
		return boardJpaRepository.findBoardWithUserAndParticipants(activityId, boardId);
	}

	@Override
	public Optional<Board> findBoardWithParticipants(Long activityId, Long boardId) {
		return boardJpaRepository.findBoardWithParticipants(activityId, boardId);
	}

	@Override
	public Optional<Board> findBoardWithUser(Long activityId, Long boardId) {
		return boardJpaRepository.findBoardWithUser(activityId, boardId);
	}

	@Override
	public void save(Board board) {
		boardJpaRepository.save(board);
	}

	@Override
	public void delete(Board board) {
		boardJpaRepository.delete(board);
	}
}