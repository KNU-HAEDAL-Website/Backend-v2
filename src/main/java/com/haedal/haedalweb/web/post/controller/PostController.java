package com.haedal.haedalweb.web.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.haedal.haedalweb.application.post.dto.BasePostRequestDto;
import com.haedal.haedalweb.application.post.dto.BasePostResponseDto;
import com.haedal.haedalweb.application.post.dto.BasePostSummaryResponseDto;
import com.haedal.haedalweb.application.post.dto.PostImageResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardRequestDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardSummaryResponseDto;
import com.haedal.haedalweb.application.post.service.PostAppService;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.domain.post.model.PostType;
import com.haedal.haedalweb.swagger.ApiErrorCodeExamples;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExample;
import com.haedal.haedalweb.util.ResponseUtil;
import com.haedal.haedalweb.web.common.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "게시글 API")
@RequiredArgsConstructor
@RestController
public class PostController {
	private final PostAppService postAppService;

	@Operation(summary = "게시글 이미지 등록")
	@ApiErrorCodeExamples({ErrorCode.BAD_REQUEST_FILE, ErrorCode.NOT_AUTHENTICATED_USER})
	@PostMapping(value = "/post-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PostImageResponseDto> registerPostImage(
		@RequestPart(value = "file") MultipartFile postImageFile) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postAppService.registerPostImage(postImageFile));
	}

	@Operation(summary = "활동 게시글 생성")
	@ApiSuccessCodeExample(SuccessCode.ADD_POST_SUCCESS)
	@ApiErrorCodeExamples({ErrorCode.NOT_AUTHENTICATED_USER, ErrorCode.NOT_FOUND_BOARD_ID,
		ErrorCode.NOT_FOUND_POST_IMAGE})
	@PostMapping("/boards/{boardId}/posts")
	public ResponseEntity<SuccessResponse> registerPostWithBoard(@PathVariable Long boardId,
		@RequestBody @Valid PostWithBoardRequestDto postWithBoardRequestDto) {
		postAppService.registerPost(boardId, postWithBoardRequestDto);

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.ADD_POST_SUCCESS);
	}

	@Operation(summary = "공지사항 게시글 생성")
	@ApiSuccessCodeExample(SuccessCode.ADD_POST_SUCCESS)
	@ApiErrorCodeExamples({ErrorCode.NOT_AUTHENTICATED_USER, ErrorCode.NOT_FOUND_BOARD_ID,
		ErrorCode.NOT_FOUND_POST_IMAGE, ErrorCode.BAD_REQUEST_POST_TYPE})
	@PostMapping("/notices")
	public ResponseEntity<SuccessResponse> registerNoticePost(
		@RequestBody @Valid BasePostRequestDto basePostRequestDto) {
		postAppService.registerPost(PostType.NOTICE, basePostRequestDto);

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.ADD_POST_SUCCESS);
	}

	@Operation(summary = "활동 게시글 삭제")
	@ApiSuccessCodeExample(SuccessCode.DELETE_POST_SUCCESS)
	@ApiErrorCodeExamples({ErrorCode.NOT_AUTHENTICATED_USER, ErrorCode.NOT_FOUND_POST_ID, ErrorCode.FORBIDDEN_UPDATE})
	@DeleteMapping("/boards/{boardId}/posts/{postId}")
	public ResponseEntity<SuccessResponse> removePostWithBoard(@PathVariable Long boardId, @PathVariable Long postId) {
		postAppService.removePost(boardId, postId);

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.DELETE_POST_SUCCESS);
	}

	@Operation(summary = "공지사항 삭제")
	@ApiSuccessCodeExample(SuccessCode.DELETE_POST_SUCCESS)
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_POST_ID})
	@DeleteMapping("/notices/{postId}")
	public ResponseEntity<SuccessResponse> removeNoticePost(@PathVariable Long postId) {
		postAppService.removePost(PostType.NOTICE, postId);

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.DELETE_POST_SUCCESS);
	}

	@Operation(summary = "활동 게시글 목록 조회")
	@GetMapping("/boards/{boardId}/posts")
	public ResponseEntity<Page<PostWithBoardSummaryResponseDto>> getPostsWithBoard(@PathVariable Long boardId,
		@RequestParam(name = "page", defaultValue = "0") Integer page,
		@RequestParam(name = "size", defaultValue = "10") Integer size) {

		Page<PostWithBoardSummaryResponseDto> posts = postAppService.getPostPage(boardId,
			PageRequest.of(page, size, Sort.by(Sort.Order.desc("id"))));

		return ResponseEntity.ok(posts);
	}

	@Operation(summary = "공지사항 게시글 목록 조회")
	@GetMapping("/notices")
	public ResponseEntity<Page<BasePostSummaryResponseDto>> getNoticePosts(
		@RequestParam(name = "page", defaultValue = "0") Integer page,
		@RequestParam(name = "size", defaultValue = "10") Integer size) {
		Page<BasePostSummaryResponseDto> posts = postAppService.getPostPage(PostType.NOTICE,
			PageRequest.of(page, size, Sort.by(Sort.Order.desc("id"))));

		return ResponseEntity.ok(posts);
	}

	@Operation(summary = "활동 게시글 단일 조회")
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_POST_ID})
	@GetMapping("/boards/{boardId}/posts/{postId}")
	public ResponseEntity<PostWithBoardResponseDto> getPostWithBoard(@PathVariable Long boardId,
		@PathVariable Long postId, HttpServletRequest httpServletRequest) {

		return ResponseEntity.ok(postAppService.getPost(boardId, postId, getClientIp(httpServletRequest)));
	}

	@Operation(summary = "공지사항 게시글 단일 조회")
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_POST_ID})
	@GetMapping("/notices/{postId}")
	public ResponseEntity<BasePostResponseDto> getNoticePost(@PathVariable Long postId,
		HttpServletRequest httpServletRequest) {

		return ResponseEntity.ok(postAppService.getPost(PostType.NOTICE, postId, getClientIp(httpServletRequest)));
	}

	@Operation(summary = "활동 게시글 수정")
	@ApiSuccessCodeExample(SuccessCode.UPDATE_POST_SUCCESS)
	@ApiErrorCodeExamples({ErrorCode.NOT_AUTHENTICATED_USER, ErrorCode.NOT_FOUND_POST_ID,
		ErrorCode.NOT_FOUND_POST_IMAGE})
	@PutMapping("/boards/{boardId}/posts/{postId}")
	public ResponseEntity<SuccessResponse> updatePostWithBoard(@PathVariable Long boardId, @PathVariable Long postId,
		@RequestBody @Valid PostWithBoardRequestDto postWithBoardRequestDto) {
		postAppService.updatePost(boardId, postId, postWithBoardRequestDto);

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_POST_SUCCESS);
	}

	@Operation(summary = "공지사항 게시글 수정")
	@ApiSuccessCodeExample(SuccessCode.UPDATE_POST_SUCCESS)
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_POST_ID, ErrorCode.NOT_FOUND_POST_IMAGE})
	@PutMapping("/notices/{postId}")
	public ResponseEntity<SuccessResponse> updateNoticePost(@PathVariable Long postId,
		@RequestBody @Valid BasePostRequestDto basePostRequestDto) {
		postAppService.updatePost(PostType.NOTICE, postId, basePostRequestDto);

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_POST_SUCCESS);
	}

	private String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		} else {
			// X-Forwarded-For 헤더에 여러 IP가 있을 경우 첫 번째 IP 사용
			ip = ip.split(",")[0].trim();
		}
		return ip;
	}
}
