package com.haedal.haedalweb.application.post.service;

import com.haedal.haedalweb.application.post.dto.BasePostRequestDto;
import com.haedal.haedalweb.application.post.dto.PostImageResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardRequestDto;
import com.haedal.haedalweb.domain.post.model.PostType;
import org.springframework.web.multipart.MultipartFile;

public interface PostAppService {
    void registerPost(Long boardId, PostWithBoardRequestDto postWithBoardRequestDto);
    void registerPost(PostType postType, BasePostRequestDto basePostRequestDto);
    PostImageResponseDto registerPostImage(MultipartFile postImageFile);
    void removePost(Long boardId, Long postId);
}
