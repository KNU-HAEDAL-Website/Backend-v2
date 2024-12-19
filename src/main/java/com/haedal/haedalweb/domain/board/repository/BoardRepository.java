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
            value = "select b from Board b " +
                    "join fetch b.boardImage " + // 여기서 OneToOne 연관 엔티티 fetch join
                    "where b.activity.id = :activityId",
            countQuery = "select count(b) from Board b where b.activity.id = :activityId"
    )
    Page<Board> findBoardsByActivityId(@Param("activityId") Long activityId, Pageable pageable);

    Optional<Board> findByActivityIdAndId(Long activityId, Long boardId);

    @Query("SELECT DISTINCT b FROM Board b " +
            "JOIN FETCH b.boardImage " +
            "JOIN FETCH b.participants " +
            "WHERE b.id = :boardId AND b.activity.id = :activityId")
    Optional<Board> findBoardWithImageAndParticipants(Long activityId, Long boardId);
}
