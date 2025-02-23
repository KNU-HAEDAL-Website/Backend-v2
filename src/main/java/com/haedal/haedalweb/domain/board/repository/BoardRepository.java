package com.haedal.haedalweb.domain.board.repository;

import com.haedal.haedalweb.domain.board.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsByActivityId(Long activityId);

    @Query(
            value = "SELECT b FROM Board b " +
                    "JOIN FETCH b.boardImage " + // 여기서 OneToOne 연관 엔티티 fetch join
                    "JOIN FETCH b.user " +
                    "WHERE b.activity.id = :activityId",
            countQuery = "SELECT count(b) FROM Board b WHERE b.activity.id = :activityId"
    )
    Page<Board> findBoardPage(@Param("activityId") Long activityId, Pageable pageable);

    @Query("SELECT DISTINCT b FROM Board b " +
            "JOIN FETCH b.boardImage " +
            "WHERE b.id = :boardId")
    Optional<Board> findBoard(Long boardId);

    @Query("SELECT DISTINCT b FROM Board b " +
            "JOIN FETCH b.user " +
            "JOIN FETCH b.boardImage " +
            "WHERE b.id = :boardId AND b.activity.id = :activityId")
    Optional<Board> findBoardWithUserAndParticipants(Long activityId, Long boardId);

    @Query("SELECT DISTINCT b FROM Board b " +
            "JOIN FETCH b.boardImage " +
            "JOIN FETCH b.participants " +
            "JOIN FETCH b.user " +
            "WHERE b.id = :boardId AND b.activity.id = :activityId")
    Optional<Board> findBoardWithParticipants(Long activityId, Long boardId);

    @Query("SELECT DISTINCT b FROM Board b " +
            "JOIN FETCH b.boardImage " +
            "JOIN FETCH b.user " +
            "WHERE b.id = :boardId AND b.activity.id = :activityId")
    Optional<Board> findBoardWithUser(Long activityId, Long boardId);
}
