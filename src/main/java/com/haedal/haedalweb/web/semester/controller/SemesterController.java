package com.haedal.haedalweb.web.semester.controller;

import com.haedal.haedalweb.application.semester.service.SemesterAppService;
import com.haedal.haedalweb.application.semester.dto.SemesterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Tag(name = "학기 API")
@RequestMapping("/semesters")
@RequiredArgsConstructor
@RestController
public class SemesterController {
    private final SemesterAppService semesterAppService;

    @Operation(summary = "학기 전체 조회")
    @GetMapping
    public ResponseEntity<List<SemesterResponseDto>> getSemesters() {
        return ResponseEntity.ok(semesterAppService.getSemesters());
    }

    @Operation(summary = "학기 단일 조회")
    @GetMapping("/{semesterId}")
    public ResponseEntity<SemesterResponseDto> getSemester(@PathVariable Long semesterId) {
        return ResponseEntity.ok(semesterAppService.getSemester(semesterId));
    }
}
