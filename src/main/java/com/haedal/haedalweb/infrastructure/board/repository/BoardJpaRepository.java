package com.haedal.haedalweb.infrastructure.board.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.haedal.haedalweb.domain.board.model.Board;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {

	boolean existsByActivityId(Long activityId);

	@Query(
		value = "SELECT b FROM Board b "
			+ "JOIN FETCH b.boardImage "  // OneToOne 연관 엔티티 fetch join
			+ "JOIN FETCH b.user "
			+ "WHERE b.activity.id = :activityId",
		countQuery = "SELECT count(b) FROM Board b WHERE b.activity.id = :activityId"
	)
	Page<Board> findBoardPage(@Param("activityId") Long activityId, Pageable pageable);

	@Query(
		"SELECT DISTINCT b FROM Board b "
			+ "JOIN FETCH b.boardImage "
			+ "WHERE b.id = :boardId"
	)
	Optional<Board> findBoard(@Param("boardId") Long boardId);

	@Query(
		"SELECT DISTINCT b FROM Board b "
			+ "JOIN FETCH b.user "
			+ "JOIN FETCH b.boardImage "
			+ "WHERE b.id = :boardId AND b.activity.id = :activityId"
	)
	Optional<Board> findBoardWithUserAndParticipants(@Param("activityId") Long activityId, @Param("boardId") Long boardId);

	@Query(
		"SELECT DISTINCT b FROM Board b "
			+ "JOIN FETCH b.boardImage "
			+ "JOIN FETCH b.participants "
			+ "JOIN FETCH b.user "
			+ "WHERE b.id = :boardId AND b.activity.id = :activityId"
	)
	Optional<Board> findBoardWithParticipants(@Param("activityId") Long activityId, @Param("boardId") Long boardId);

	@Query(
		"SELECT DISTINCT b FROM Board b "
			+ "JOIN FETCH b.boardImage "
			+ "JOIN FETCH b.user "
			+ "WHERE b.id = :boardId AND b.activity.id = :activityId"
	)
	Optional<Board> findBoardWithUser(@Param("activityId") Long activityId, @Param("boardId") Long boardId);
}