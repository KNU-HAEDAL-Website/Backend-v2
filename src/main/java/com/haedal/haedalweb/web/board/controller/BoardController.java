package com.haedal.haedalweb.web.board.controller;

import com.haedal.haedalweb.application.board.dto.BoardRequestDto;
import com.haedal.haedalweb.application.board.service.BoardAppService;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.application.board.dto.BoardResponseDto;
import com.haedal.haedalweb.web.common.dto.SuccessResponse;
import com.haedal.haedalweb.swagger.ApiErrorCodeExamples;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExample;
import com.haedal.haedalweb.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

@Tag(name = "게시판 API")
@RequiredArgsConstructor
@RestController
@Slf4j
public class BoardController {
    private final BoardAppService boardAppService;

    @Operation(summary = "활동 게시판 생성")
    @ApiSuccessCodeExample(SuccessCode.ADD_BOARD_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID, ErrorCode.NOT_FOUND_ACTIVITY_ID, ErrorCode.BAD_REQUEST_FILE, ErrorCode.NOT_AUTHENTICATED_USER})
    @PostMapping(value = "/activities/{activityId}/boards", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> registerBoard(@PathVariable Long activityId, @RequestPart(value = "file") MultipartFile boardImageFile, @RequestPart @Valid BoardRequestDto boardRequestDto) {
        boardAppService.registerBoard(activityId, boardImageFile, boardRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.ADD_BOARD_SUCCESS);
    }
    @Operation(summary = "활동 게시판 단일 조회")
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_BOARD_ID})
    @GetMapping("/activities/{activityId}/boards/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long activityId, @PathVariable Long boardId) {

        return ResponseEntity.ok(boardAppService.getBoard(activityId, boardId));
    }

    @Operation(summary = "활동 게시판 페이징 조회")
    @GetMapping("/activities/{activityId}/boards")
    public ResponseEntity<Page<BoardResponseDto>> getBoards(@PathVariable Long activityId,
                                                            @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Page<BoardResponseDto> boardDtos = boardAppService.getBoardPage(activityId, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id"))));

        return ResponseEntity.ok(boardDtos);
    }
    @Operation(summary = "활동 게시판 이미지 수정")
    @ApiSuccessCodeExample(SuccessCode.UPDATE_BOARD_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_BOARD_ID, ErrorCode.FORBIDDEN_UPDATE, ErrorCode.BAD_REQUEST_FILE, ErrorCode.NOT_AUTHENTICATED_USER})
    @PutMapping(value = "/activities/{activityId}/boards/{boardId}/image",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> updateBoardImage(@PathVariable Long activityId, @PathVariable Long boardId, @RequestPart(value = "file") MultipartFile boardImageFile) {
        boardAppService.updateBoardImage(activityId, boardId, boardImageFile);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_BOARD_SUCCESS);
    }
    @Operation(summary = "활동 게시판 메타 데이터 수정")
    @ApiSuccessCodeExample(SuccessCode.UPDATE_BOARD_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_BOARD_ID, ErrorCode.FORBIDDEN_UPDATE, ErrorCode.NOT_FOUND_USER_ID, ErrorCode.NOT_AUTHENTICATED_USER})
    @PutMapping("/activities/{activityId}/boards/{boardId}")
    public ResponseEntity<SuccessResponse> updateBoard(@PathVariable Long activityId, @PathVariable Long boardId, @RequestBody @Valid BoardRequestDto boardRequestDto) {
        boardAppService.updateBoard(activityId, boardId, boardRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_BOARD_SUCCESS);
    }


    @Operation(summary = "활동 게시판 삭제")
    @ApiSuccessCodeExample(SuccessCode.DELETE_BOARD_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_BOARD_ID, ErrorCode.FORBIDDEN_UPDATE, ErrorCode.NOT_AUTHENTICATED_USER})
    @DeleteMapping("/activities/{activityId}/boards/{boardId}")
    public ResponseEntity<SuccessResponse> deleteBoard(@PathVariable Long activityId, @PathVariable Long boardId) {
        boardAppService.removeBoard(activityId, boardId);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.DELETE_BOARD_SUCCESS);
    }
}
