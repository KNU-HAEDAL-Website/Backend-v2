package com.haedal.haedalweb.web.activity.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.haedal.haedalweb.application.activity.dto.ActivityResponseDto;
import com.haedal.haedalweb.application.activity.service.ActivityAppService;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.swagger.ApiErrorCodeExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "활동 API")
@RequiredArgsConstructor
@RestController
public class ActivityController {
	private final ActivityAppService activityAppService;

	@Operation(summary = "해당 학기 활동 조회")
	@GetMapping("/semesters/{semesterId}/activities")
	public ResponseEntity<List<ActivityResponseDto>> getActivities(@PathVariable Long semesterId) {
		return ResponseEntity.ok(activityAppService.getActivities(semesterId));
	}

	@Operation(summary = "해당 학기 활동 단일 조회")
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_ACTIVITY_ID})
	@GetMapping("/semesters/{semesterId}/activities/{activityId}")
	public ResponseEntity<ActivityResponseDto> getActivity(@PathVariable Long semesterId,
		@PathVariable Long activityId) {
		return ResponseEntity.ok(activityAppService.getActivity(semesterId, activityId));
	}
}
