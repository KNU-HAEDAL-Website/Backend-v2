package com.haedal.haedalweb.application.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDto {
    @Schema(description = "댓글 id")
    private Long commentId;

    @Schema(description = "댓글 내용")
    private String commentContent;

    @Schema(description = "댓글 작성자Id")
    private String userId;

    @Schema(description = "댓글 작성자 이름")
    private String userName;

    @Schema(description = "게시글 id")
    private Long postId;

    @Schema(description = "삭제 여부")
    private boolean deleted;

    @Schema(description = "답글 리스트")
    private List<CommentResponseDto> replies;

    @Schema(description = "댓글 작성 시간")
    private LocalDateTime commentRegDate;
}
