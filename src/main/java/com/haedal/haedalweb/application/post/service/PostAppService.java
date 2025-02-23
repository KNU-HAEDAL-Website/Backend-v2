package com.haedal.haedalweb.application.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.haedal.haedalweb.application.post.dto.BasePostRequestDto;
import com.haedal.haedalweb.application.post.dto.BasePostResponseDto;
import com.haedal.haedalweb.application.post.dto.BasePostSummaryResponseDto;
import com.haedal.haedalweb.application.post.dto.PostImageResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardRequestDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardSummaryResponseDto;
import com.haedal.haedalweb.domain.post.model.PostType;

public interface PostAppService {
	void registerPost(Long boardId, PostWithBoardRequestDto postWithBoardRequestDto);

	void registerPost(PostType postType, BasePostRequestDto basePostRequestDto);

	PostImageResponseDto registerPostImage(MultipartFile postImageFile);

	void removePost(Long boardId, Long postId);

	void removePost(PostType postType, Long postId);

	Page<PostWithBoardSummaryResponseDto> getPostPage(Long boardId, Pageable pageable);

	Page<BasePostSummaryResponseDto> getPostPage(PostType postType, Pageable pageable);

	PostWithBoardResponseDto getPost(Long boardId, Long postId, String clientIp);

	BasePostResponseDto getPost(PostType postType, Long postId, String clientIp);

	void updatePost(Long boardId, Long postId, PostWithBoardRequestDto postWithBoardRequestDto);

	void updatePost(PostType postType, Long postId, BasePostRequestDto basePostRequestDto);
}
