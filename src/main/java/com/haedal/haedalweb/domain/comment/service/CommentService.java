package com.haedal.haedalweb.domain.comment.service;

import com.haedal.haedalweb.domain.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    void registerComment(Comment comment);

    Page<Comment> getCommentPage(Long postId, Pageable pageable);

    Comment getComment(Long commentId);
}
