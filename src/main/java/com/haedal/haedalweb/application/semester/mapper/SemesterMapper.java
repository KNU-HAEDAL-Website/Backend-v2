package com.haedal.haedalweb.application.semester.mapper;

import com.haedal.haedalweb.application.semester.dto.SemesterResponseDto;
import com.haedal.haedalweb.domain.semester.model.Semester;
import org.springframework.stereotype.Component;

@Component
public interface SemesterMapper {
    SemesterResponseDto toDto(Semester semester);
}
