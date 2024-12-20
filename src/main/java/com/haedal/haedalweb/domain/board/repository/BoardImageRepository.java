package com.haedal.haedalweb.domain.board.repository;

import com.haedal.haedalweb.domain.board.model.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
}
