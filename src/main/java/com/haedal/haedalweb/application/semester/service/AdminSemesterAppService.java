package com.haedal.haedalweb.application.semester.service;

import com.haedal.haedalweb.application.semester.dto.CreateSemesterRequestDto;

public interface AdminSemesterAppService {
    void registerSemester(CreateSemesterRequestDto createSemesterRequestDto);
    void removeSemester(Long semesterId);
}
