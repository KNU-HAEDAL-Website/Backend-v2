package com.haedal.haedalweb.domain.semester.service;

import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.web.semester.dto.SemesterResponseDto;
import java.util.List;

public interface SemesterService {

    List<SemesterResponseDto> getAllSemesterDTOs();
    Semester findSemesterById(Long semesterId);
}
