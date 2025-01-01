package com.haedal.haedalweb.application.post.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.haedal.haedalweb.domain.post.model.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasePostResponseDto {
    @Schema(description = "게시글 id")
    private Long postId;

    @Schema(description = "게시글 제목")
    private String postTitle;

    @Schema(description = "게시글 내용")
    private String postContent;

    @Schema(description = "게시글 조회수")
    private Long postViews;

    @Schema(description = "게시글 타입", example = "(NOTICE, ACTIVITY)")
    private PostType postType;

    @Schema(description = "게시글 생성일")
    private LocalDateTime postRegDate;

    @Schema(description = "유저 아이디", example = "haedal12")
    private String userId;

    @Schema(description = "유저 이름", example = "조대성")
    private String userName;
}
