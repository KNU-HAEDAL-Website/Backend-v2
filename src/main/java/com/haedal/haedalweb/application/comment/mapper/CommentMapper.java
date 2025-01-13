package com.haedal.haedalweb.application.comment.mapper;

import com.haedal.haedalweb.application.comment.dto.CommentResponseDto;
import com.haedal.haedalweb.domain.comment.model.Comment;
import java.util.Collections;
import java.util.List;

public class CommentMapper {
    private CommentMapper() {
    }

    public static CommentResponseDto toDto(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.isDeleted() ? null : comment.getId())
                .commentContent(comment.isDeleted() ? "삭제된 댓글입니다." : comment.getContent())
                .replies(toRepliesDto(comment.getReplies()))
                .userId(comment.isDeleted() ? null : comment.getUser().getId())
                .userName(comment.isDeleted() ? null : comment.getUser().getName())
                .postId(comment.getPost().getId())
                .deleted(comment.isDeleted())
                .commentRegDate(comment.isDeleted() ? null : comment.getRegDate())
                .build();
    }

    private static List<CommentResponseDto> toRepliesDto(List<Comment> comments) {
        if (comments != null && !comments.isEmpty()) {
            return comments.stream()
                    .map(CommentMapper::toDto)
                    .toList();
        }

        return Collections.emptyList();
    }
}
