package com.haedal.haedalweb.domain.board.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.haedal.haedalweb.domain.board.model.Board;

public interface BoardRepository {
	boolean existsByActivityId(Long activityId);

	Page<Board> findBoardPage(Long activityId, Pageable pageable);

	Optional<Board> findBoard(Long boardId);

	Optional<Board> findBoardWithUserAndParticipants(Long activityId, Long boardId);

	Optional<Board> findBoardWithParticipants(Long activityId, Long boardId);

	Optional<Board> findBoardWithUser(Long activityId, Long boardId);

	void save(Board board);

	void delete(Board board);
}
