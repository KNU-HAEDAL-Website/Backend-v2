package com.haedal.haedalweb.web.activity.controller;

import com.haedal.haedalweb.application.activity.service.ActivityAppService;
import com.haedal.haedalweb.application.activity.dto.ActivityResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
}
