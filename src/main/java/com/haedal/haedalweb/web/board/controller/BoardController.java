package com.haedal.haedalweb.web.board.controller;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.domain.board.service.BoardService;
import com.haedal.haedalweb.web.board.dto.CreateBoardRequestDto;
import com.haedal.haedalweb.web.board.dto.UpdateBoardRequestDto;
import com.haedal.haedalweb.web.board.dto.BoardResponseDto;
import com.haedal.haedalweb.web.common.dto.SuccessResponse;
import com.haedal.haedalweb.swagger.ApiErrorCodeExamples;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExample;
import com.haedal.haedalweb.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final BoardService boardService;

    @Operation(summary = "게시판 생성")
    @ApiSuccessCodeExample(SuccessCode.ADD_BOARD_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID, ErrorCode.NOT_FOUND_ACTIVITY_ID})
    @Parameter(name = "activityId", description = "게시판 추가할 활동 ID")
    @PostMapping(value = "/activities/{activityId}/boards", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> addBoard(@PathVariable Long activityId, @RequestPart("file") MultipartFile boardImage, @RequestPart @Valid CreateBoardRequestDto createBoardRequestDto) {
        boardService.createBoard(activityId, createBoardRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.ADD_BOARD_SUCCESS);
    }

    @Operation(summary = "게시판 페이징 조회")
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_BOARD_ID, ErrorCode.NOT_FOUND_ACTIVITY_ID})
    @Parameters({
            @Parameter(name = "activityId", description = "게시판 조회할 활동 ID"),
            @Parameter(name = "page", description = "조회 할 page, default: 0"),
            @Parameter(name = "size", description = "한 번에 조회 할 page 수, default: 5")
    })
    @GetMapping("/activities/{activityId}/boards")
    public ResponseEntity<Page<BoardResponseDto>> getBoards(@PathVariable Long activityId,
                                                            @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Page<BoardResponseDto> boardDTOs = boardService.getBoardDTOs(activityId, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id"))));

        return ResponseEntity.ok(boardDTOs);
    }

    @Operation(summary = "게시판 단일 조회")
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_BOARD_ID})
    @Parameters({
            @Parameter(name = "activityId", description = "게시판 조회할 활동 ID"),
            @Parameter(name = "boardId", description = "해당 게시판 ID")
    })
    @GetMapping("/activities/{activityId}/boards/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long activityId, @PathVariable Long boardId) {
        BoardResponseDto boardResponseDto = boardService.getBoardDTO(activityId, boardId);

        return ResponseEntity.ok(boardResponseDto);
    }

    @Operation(summary = "게시판 삭제")
    @ApiSuccessCodeExample(SuccessCode.DELETE_BOARD_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_BOARD_ID, ErrorCode.FORBIDDEN_UPDATE})
    @Parameters({
            @Parameter(name = "activityId", description = "게시판 삭제할 활동 ID"),
            @Parameter(name = "boardId", description = "해당 게시판 ID")
    })
    @DeleteMapping("/activities/{activityId}/boards/{boardId}")
    public ResponseEntity<SuccessResponse> deleteBoard(@PathVariable Long activityId, @PathVariable Long boardId) {
        boardService.deleteBoard(activityId, boardId);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.DELETE_BOARD_SUCCESS);
    }

    @Operation(summary = "게시판 이미지 수정")
    @ApiSuccessCodeExample(SuccessCode.UPDATE_BOARD_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_BOARD_ID, ErrorCode.FORBIDDEN_UPDATE})
    @Parameters({
            @Parameter(name = "activityId", description = "게시판 삭제할 활동 ID"),
            @Parameter(name = "boardId", description = "해당 게시판 ID")
    })
    @PatchMapping("/activities/{activityId}/boards/{boardId}/image")
    public ResponseEntity<SuccessResponse> updateBoardImage(@PathVariable Long activityId, @PathVariable Long boardId, @RequestBody String boardImageUrl) {
        boardService.updateBoardImage(activityId, boardId, boardImageUrl);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_BOARD_SUCCESS);
    }

    @Operation(summary = "게시판 메타 데이터 수정")
    @ApiSuccessCodeExample(SuccessCode.UPDATE_BOARD_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_BOARD_ID, ErrorCode.FORBIDDEN_UPDATE, ErrorCode.NOT_FOUND_USER_ID})
    @Parameters({
            @Parameter(name = "activityId", description = "게시판 수정할 활동 ID"),
            @Parameter(name = "boardId", description = "해당 게시판 ID")
    })
    @PatchMapping("/activities/{activityId}/boards/{boardId}")
    public ResponseEntity<SuccessResponse> updateBoard(@PathVariable Long activityId, @PathVariable Long boardId, @RequestBody @Valid UpdateBoardRequestDto updateBoardRequestDto) {
        boardService.updateBoard(activityId, boardId, updateBoardRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_BOARD_SUCCESS);
    }
}
