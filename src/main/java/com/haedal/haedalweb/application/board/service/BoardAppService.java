package com.haedal.haedalweb.application.board.service;

import com.haedal.haedalweb.web.board.dto.CreateBoardRequestDto;

public interface BoardAppService {
    void registerBoard(Long activityId, CreateBoardRequestDto createBoardRequestDto);
}
