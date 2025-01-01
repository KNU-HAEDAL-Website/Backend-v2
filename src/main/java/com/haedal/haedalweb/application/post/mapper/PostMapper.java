package com.haedal.haedalweb.application.post.mapper;

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
}
