package com.haedal.haedalweb.application.semester.service;

import com.haedal.haedalweb.application.semester.dto.SemesterResponseDto;

import java.util.List;

public interface SemesterAppService {
    List<SemesterResponseDto> getSemesters();
    SemesterResponseDto getSemester(Long semesterId);
}
