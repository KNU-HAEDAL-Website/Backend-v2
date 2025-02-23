package com.haedal.haedalweb.application.comment.mapper;

import java.util.Collections;
import java.util.List;

import com.haedal.haedalweb.application.comment.dto.CommentResponseDto;
import com.haedal.haedalweb.domain.comment.model.Comment;

public class CommentMapper {
	private CommentMapper() {
	}

	public static CommentResponseDto toDto(Comment comment) {
		return CommentResponseDto.builder()
			.commentId(comment.getId())
			.commentContent(comment.isDeleted() ? "삭제된 댓글입니다." : comment.getContent())
			.replies(toRepliesDto(comment.getReplies()))
			.userId(comment.getUser().getId())
			.userName(comment.getUser().getName())
			.postId(comment.getPost().getId())
			.deleted(comment.isDeleted())
			.commentRegDate(comment.getRegDate())
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
