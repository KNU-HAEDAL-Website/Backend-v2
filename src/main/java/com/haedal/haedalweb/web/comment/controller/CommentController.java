package com.haedal.haedalweb.web.comment.controller;

import com.haedal.haedalweb.application.comment.dto.CommentRequestDto;
import com.haedal.haedalweb.application.comment.dto.CommentResponseDto;
import com.haedal.haedalweb.application.comment.service.CommentAppService;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.swagger.ApiErrorCodeExamples;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExample;
import com.haedal.haedalweb.util.ResponseUtil;
import com.haedal.haedalweb.web.common.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글 API")
@RequiredArgsConstructor
@RestController
@Slf4j
public class CommentController {
    private final CommentAppService commentAppService;

    @Operation(summary = "댓글 등록")
    @ApiSuccessCodeExample(SuccessCode.ADD_COMMENT_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_AUTHENTICATED_USER, ErrorCode.NOT_FOUND_POST_ID})
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<SuccessResponse> registerComment(@PathVariable Long postId, @RequestBody @Valid CommentRequestDto commentRequestDto) {
        commentAppService.registerComment(postId, commentRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.ADD_COMMENT_SUCCESS);
    }

    @Operation(summary = "댓글 페이징 조회")
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<Page<CommentResponseDto>> getComments(@PathVariable Long postId,
                                                                @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                @RequestParam(name = "size", defaultValue = "10") Integer size) {

        Page<CommentResponseDto> comments = commentAppService.getCommentPage(postId, PageRequest.of(page, size, Sort.by(Sort.Order.desc("id"))));

        return ResponseEntity.ok(comments);
    }
}
