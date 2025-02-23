package com.haedal.haedalweb.application.semester.service;

import com.haedal.haedalweb.application.semester.dto.SemesterRequestDto;

public interface AdminSemesterAppService {
	void registerSemester(SemesterRequestDto semesterRequestDto);

	void removeSemester(Long semesterId);
}
