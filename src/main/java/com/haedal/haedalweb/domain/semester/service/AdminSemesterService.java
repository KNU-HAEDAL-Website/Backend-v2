package com.haedal.haedalweb.domain.semester.service;

import com.haedal.haedalweb.application.semester.dto.CreateSemesterRequestDto;


public interface AdminSemesterService {

    void createSemester(CreateSemesterRequestDto createSemesterRequestDto);

    void deleteSemester(Long semesterId);
}
