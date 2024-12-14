package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.web.post.dto.CreatePostRequestDto;
import com.haedal.haedalweb.web.post.dto.PostResponseDto;
import com.haedal.haedalweb.web.post.dto.PostSliderResponseDto;
import com.haedal.haedalweb.web.post.dto.PostSummaryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    void createPost(Long boardId, CreatePostRequestDto createPostRequestDTO);

    void createPost(CreatePostRequestDto createPostRequestDTO);

    void deletePost(Long boardId, Long postId);

    void deletePost(Long postId);

    Page<PostSummaryResponseDto> getPosts(Long boardId, Pageable pageable);

    Page<PostSummaryResponseDto> getPosts(String pType, Pageable pageable);

    PostResponseDto getPost(Long postId);

    Page<PostSliderResponseDto> getSliderPosts(Pageable pageable);
}
