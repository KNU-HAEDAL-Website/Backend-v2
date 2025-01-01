package com.haedal.haedalweb.application.post.mapper;

import com.haedal.haedalweb.application.post.dto.BasePostResponseDto;
import com.haedal.haedalweb.application.post.dto.BasePostSummaryResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardSummaryResponseDto;
import com.haedal.haedalweb.domain.post.model.Post;

public class PostMapper {
    private PostMapper() {
    }

    public static PostWithBoardSummaryResponseDto toPostWithBoardSummaryResponseDto(Post post) {
        return PostWithBoardSummaryResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postViews(0L)
                .postRegDate(post.getRegDate())
                .postType(post.getPostType())
                .userId(post.getUser().getId())
                .userName(post.getUser().getName())
                .boardId(post.getBoard().getId())
                .postActivityStartDate(post.getActivityStartDate())
                .postActivityEndDate(post.getActivityEndDate())
                .build();
    }

    public static BasePostSummaryResponseDto toBasePostSummaryResponseDto(Post post) {
        return PostWithBoardSummaryResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postViews(0L)
                .postRegDate(post.getRegDate())
                .postType(post.getPostType())
                .userId(post.getUser().getId())
                .userName(post.getUser().getName())
                .build();
    }

    public static PostWithBoardResponseDto toPostWithBoardResponseDto(Post post) {
        return PostWithBoardResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .postViews(0L)
                .postRegDate(post.getRegDate())
                .postType(post.getPostType())
                .userId(post.getUser().getId())
                .userName(post.getUser().getName())
                .boardId(post.getBoard().getId())
                .postActivityStartDate(post.getActivityStartDate())
                .postActivityEndDate(post.getActivityEndDate())
                .build();
    }

    public static BasePostResponseDto toBasePostResponseDto(Post post) {
        return BasePostResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .postViews(0L)
                .postRegDate(post.getRegDate())
                .postType(post.getPostType())
                .userId(post.getUser().getId())
                .userName(post.getUser().getName())
                .build();
    }
}
