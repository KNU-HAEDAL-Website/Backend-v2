package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.application.post.dto.BasePostRequestDto;
import com.haedal.haedalweb.application.post.dto.PostResponseDto;
import com.haedal.haedalweb.application.post.dto.PostSummaryResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardRequestDto;
import com.haedal.haedalweb.domain.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    void registerPost(Post post);

    void createPost(BasePostRequestDto basePostRequestDTO);

    void deletePost(Long boardId, Long postId);

    void deletePost(Long postId);

    Page<PostSummaryResponseDto> getPosts(Long boardId, Pageable pageable);

    Page<PostSummaryResponseDto> getPosts(String pType, Pageable pageable);

    PostResponseDto getPost(Long postId);
}
