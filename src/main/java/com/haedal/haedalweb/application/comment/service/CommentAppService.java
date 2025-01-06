package com.haedal.haedalweb.application.comment.service;

import com.haedal.haedalweb.application.comment.dto.CommentRequestDto;

public interface CommentAppService {
    void registerComment(Long postId, CommentRequestDto commentRequestDto);
}
