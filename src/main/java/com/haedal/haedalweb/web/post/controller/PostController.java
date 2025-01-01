package com.haedal.haedalweb.web.post.controller;

import com.haedal.haedalweb.application.post.dto.BasePostRequestDto;
import com.haedal.haedalweb.application.post.dto.PostImageResponseDto;
import com.haedal.haedalweb.application.post.dto.PostWithBoardRequestDto;
import com.haedal.haedalweb.application.post.service.PostAppService;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.application.post.dto.PostResponseDto;
import com.haedal.haedalweb.application.post.dto.PostSummaryResponseDto;
import com.haedal.haedalweb.domain.post.model.PostType;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.web.common.dto.SuccessResponse;
import com.haedal.haedalweb.domain.post.service.PostService;
import com.haedal.haedalweb.swagger.ApiErrorCodeExamples;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExample;
import com.haedal.haedalweb.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "게시글 API")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;
    private final PostAppService postAppService;

    @Operation(summary = "게시글 이미지 등록")
    @ApiErrorCodeExamples({ErrorCode.BAD_REQUEST_FILE, ErrorCode.NOT_AUTHENTICATED_USER})
    @PostMapping(value = "/post-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostImageResponseDto> registerPostImage(@RequestPart(value = "file") MultipartFile postImageFile) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postAppService.registerPostImage(postImageFile));
    }

    @Operation(summary = "활동 게시글 생성")
    @ApiSuccessCodeExample(SuccessCode.ADD_POST_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_AUTHENTICATED_USER, ErrorCode.NOT_FOUND_BOARD_ID, ErrorCode.NOT_FOUND_POST_IMAGE})
    @Parameter(name = "boardId", description = "게시글 추가할 게시판 ID")
    @PostMapping("/boards/{boardId}/posts")
    public ResponseEntity<SuccessResponse> registerPostWithBoard(@PathVariable Long boardId, @RequestBody @Valid PostWithBoardRequestDto postWithBoardRequestDto) {
        postAppService.registerPost(boardId, postWithBoardRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.ADD_POST_SUCCESS);
    }

    @Operation(summary = "공지사항 게시글 생성")
    @ApiSuccessCodeExample(SuccessCode.ADD_POST_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_AUTHENTICATED_USER, ErrorCode.NOT_FOUND_BOARD_ID, ErrorCode.NOT_FOUND_POST_IMAGE, ErrorCode.BAD_REQUEST_POST_TYPE})
    @PostMapping("/notices")
    public ResponseEntity<SuccessResponse> registerPost(@RequestBody @Valid BasePostRequestDto basePostRequestDto) {
        postAppService.registerPost(PostType.NOTICE, basePostRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.ADD_POST_SUCCESS);
    }

    @Operation(summary = "활동 게시글 삭제")
    @ApiSuccessCodeExample(SuccessCode.DELETE_POST_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_POST_ID, ErrorCode.NOT_FOUND_BOARD_ID, ErrorCode.FORBIDDEN_UPDATE})
    @DeleteMapping("/boards/{boardId}/posts/{postId}")
    public ResponseEntity<SuccessResponse> deletePost(@PathVariable Long boardId, @PathVariable Long postId) {
        postAppService.removePost(boardId, postId);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.DELETE_POST_SUCCESS);
    }

    @Operation(summary = "공지사항, 이벤트 게시글 삭제")
    @ApiSuccessCodeExample(SuccessCode.DELETE_POST_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_POST_ID, ErrorCode.BAD_REQUEST_POST_TYPE})
    @Parameters({
            @Parameter(name = "postId", description = "해당 게시글 ID")
    })
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<SuccessResponse> deleteNoticePost(@PathVariable Long postId) {
//        postService.deletePost(postId);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.DELETE_POST_SUCCESS);
    }

    @Operation(summary = "활동 게시글 목록 조회")
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_BOARD_ID, ErrorCode.NOT_FOUND_POST_ID})
    @Parameters({
            @Parameter(name = "boardId", description = "게시글 조회할 게시판 ID"),
            @Parameter(name = "page", description = "조회 할 page, default: 0"),
            @Parameter(name = "size", description = "한 번에 조회 할 page 수, default: 10")
    })
    @GetMapping("/boards/{boardId}/posts")
    public ResponseEntity<Page<PostSummaryResponseDto>>  getActivityPosts(@PathVariable Long boardId,
                                                                          @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                          @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Page<PostSummaryResponseDto> posts = postService.getPosts(boardId, PageRequest.of(page, size, Sort.by(Sort.Order.desc("id"))));

        return ResponseEntity.ok(posts);
    }

//    @Operation(summary = "공지사항, 이벤트 게시글 목록 조회")
//    @ApiErrorCodeExamples({ErrorCode.BAD_REQUEST_POST_TYPE, ErrorCode.NOT_FOUND_POST_ID})
//    @Parameters({
//            @Parameter(name = "page", description = "조회 할 page, default: 0"),
//            @Parameter(name = "size", description = "한 번에 조회 할 page 수, default: 10")
//    })
//    @GetMapping("/posts")
//    public ResponseEntity<Page<PostSummaryResponseDto>>  getActivityPosts(@RequestParam(name = "postType") String postType,
//                                                                          @RequestParam(name = "page", defaultValue = "0") Integer page,
//                                                                          @RequestParam(name = "size", defaultValue = "10") Integer size) {
//        Page<PostSummaryResponseDto> posts = postService.getPosts(postType, PageRequest.of(page, size, Sort.by(Sort.Order.desc("id"))));
//
//        return ResponseEntity.ok(posts);
//    }

    @Operation(summary = "게시글 단일 조회")
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_POST_ID})
    @Parameters({
            @Parameter(name = "postId", description = "해당 게시글 ID")
    })
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto post = postService.getPost(postId);

        return ResponseEntity.ok(post);
    }
}
