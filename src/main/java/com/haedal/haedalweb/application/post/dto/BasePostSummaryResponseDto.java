package com.haedal.haedalweb.application.post.dto;

import com.haedal.haedalweb.domain.post.model.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasePostSummaryResponseDto {
    @Schema(description = "게시글 id")
    private Long postId;

    @Schema(description = "게시글 제목")
    private String postTitle;

    @Schema(description = "게시글 조회수")
    private Long postViews;

    @Schema(description = "게시글 작성자 아이디", example = "haedal12")
    private String userId;

    @Schema(description = "게시글 작성자 이름", example = "조대성")
    private String userName;

    @Schema(description = "게시글 타입", example = "(NOTICE, ACTIVITY)")
    private PostType postType;

    @Schema(description = "게시글 생성일")
    private LocalDateTime postRegDate;
}
