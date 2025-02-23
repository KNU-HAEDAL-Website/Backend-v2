package com.haedal.haedalweb.application.semester.service;

import java.util.List;

import com.haedal.haedalweb.application.semester.dto.SemesterResponseDto;

public interface SemesterAppService {
	List<SemesterResponseDto> getSemesters();

	SemesterResponseDto getSemester(Long semesterId);
}
