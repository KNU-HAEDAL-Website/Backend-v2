package com.haedal.haedalweb.application.board.service;

import com.haedal.haedalweb.application.board.dto.CreateBoardRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface BoardAppService {
    void registerBoard(Long activityId, MultipartFile boardImageFile, CreateBoardRequestDto createBoardRequestDto);
}
