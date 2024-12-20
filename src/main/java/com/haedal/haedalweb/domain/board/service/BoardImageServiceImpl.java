package com.haedal.haedalweb.domain.board.service;

import com.haedal.haedalweb.domain.board.model.BoardImage;
import com.haedal.haedalweb.domain.board.repository.BoardImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardImageServiceImpl implements BoardImageService {
    private final BoardImageRepository boardImageRepository;

    @Override
    public void removeBoardImage(BoardImage boardImage) {
        boardImageRepository.delete(boardImage);
    }
}
