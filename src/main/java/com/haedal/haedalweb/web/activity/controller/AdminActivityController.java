package com.haedal.haedalweb.web.activity.controller;

import com.haedal.haedalweb.application.activity.service.AdminActivityAppService;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.application.activity.dto.ActivityRequestDto;
import com.haedal.haedalweb.web.common.dto.SuccessResponse;
import com.haedal.haedalweb.swagger.ApiErrorCodeExample;
import com.haedal.haedalweb.swagger.ApiErrorCodeExamples;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExample;
import com.haedal.haedalweb.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자 - 활동 관리 API")
@RequestMapping("/admin/semesters/{semesterId}/activities")
@RequiredArgsConstructor
@RestController
public class AdminActivityController {
    private final AdminActivityAppService adminActivityAppService;

    @Operation(summary = "활동 추가")
    @ApiSuccessCodeExample(SuccessCode.ADD_ACTIVITY_SUCCESS)
    @ApiErrorCodeExample(ErrorCode.NOT_FOUND_SEMESTER_ID)
    @PostMapping
    public ResponseEntity<SuccessResponse> registerActivity(@PathVariable Long semesterId, @RequestBody @Valid ActivityRequestDto activityRequestDto) {
        adminActivityAppService.registerActivity(semesterId, activityRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.ADD_ACTIVITY_SUCCESS);
    }

    @Operation(summary = "활동 삭제")
    @ApiSuccessCodeExample(SuccessCode.DELETE_ACTIVITY_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_ACTIVITY_ID, ErrorCode.EXIST_BOARD})
    @DeleteMapping("/{activityId}")
    public ResponseEntity<SuccessResponse> removeActivity(@PathVariable Long semesterId, @PathVariable Long activityId) {
        adminActivityAppService.removeActivity(semesterId, activityId);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.DELETE_ACTIVITY_SUCCESS);
    }
}
