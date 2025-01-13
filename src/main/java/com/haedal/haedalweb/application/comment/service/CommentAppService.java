package com.haedal.haedalweb.application.comment.service;

import com.haedal.haedalweb.application.comment.dto.CommentRequestDto;
import com.haedal.haedalweb.application.comment.dto.CommentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentAppService {
    void registerComment(Long postId, CommentRequestDto commentRequestDto);
    Page<CommentResponseDto> getCommentPage(Long postId, Pageable pageable);
}
