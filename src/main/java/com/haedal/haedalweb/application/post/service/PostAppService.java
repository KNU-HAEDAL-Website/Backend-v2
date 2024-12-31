package com.haedal.haedalweb.application.post.service;

import com.haedal.haedalweb.application.post.dto.PostImageResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface PostAppService {
    PostImageResponseDto registerPostImage(MultipartFile postImageFile);
    void registerPost(Long boardId, PostWithBoardRequestDto postWithBoardRequestDto);
}
