package com.haedal.haedalweb.domain.semester.service;

import com.haedal.haedalweb.web.semester.dto.SemesterResponseDto;
import java.util.List;

public interface SemesterService {

    List<SemesterResponseDto> getAllSemesterDTOs();
}
