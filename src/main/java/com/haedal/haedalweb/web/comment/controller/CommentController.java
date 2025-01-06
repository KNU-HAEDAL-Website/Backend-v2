package com.haedal.haedalweb.web.comment.controller;

import com.haedal.haedalweb.application.comment.dto.CommentRequestDto;
import com.haedal.haedalweb.application.comment.service.CommentAppService;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExample;
import com.haedal.haedalweb.util.ResponseUtil;
import com.haedal.haedalweb.web.common.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글 API")
@RequiredArgsConstructor
@RestController
@Slf4j
public class CommentController {
    private final CommentAppService commentAppService;

    @Operation(summary = "댓글 생성")
    @ApiSuccessCodeExample(SuccessCode.ADD_COMMENT_SUCCESS)
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<SuccessResponse> registerComment(@PathVariable Long postId, @RequestBody @Valid CommentRequestDto commentRequestDto) {
        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.ADD_COMMENT_SUCCESS);
    }
}
