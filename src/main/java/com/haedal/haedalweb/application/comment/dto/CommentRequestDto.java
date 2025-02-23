package com.haedal.haedalweb.application.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {
	@Schema(description = "댓글 내용", example = "우와 이 게시글 너무 좋아요")
	@Size(min = 1, max = 1000, message = "댓글 내용은 1자 이상 1000자 이하여야 합니다.")
	private String commentContent;
}
