package com.haedal.haedalweb.domain.comment.service;

import com.haedal.haedalweb.domain.comment.model.Comment;
import com.haedal.haedalweb.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public void registerComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Page<Comment> getCommentPage(Long postId, Pageable pageable) {
        return commentRepository.findCommentPageId(postId, pageable);
    }
}
