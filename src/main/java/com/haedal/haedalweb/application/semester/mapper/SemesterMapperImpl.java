package com.haedal.haedalweb.application.semester.mapper;

import com.haedal.haedalweb.application.semester.dto.SemesterResponseDto;
import com.haedal.haedalweb.domain.semester.model.Semester;
import org.springframework.stereotype.Component;

@Component
public class SemesterMapperImpl implements SemesterMapper {
    @Override
    public SemesterResponseDto toDto(Semester semester) {
        return SemesterResponseDto.builder()
                .semesterId(semester.getId())
                .semesterName(semester.getName())
                .build();
    }
}
