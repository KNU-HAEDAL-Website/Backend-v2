package com.haedal.haedalweb.application.semester.mapper;

import com.haedal.haedalweb.application.semester.dto.CreateSemesterRequestDto;
import com.haedal.haedalweb.application.semester.dto.SemesterResponseDto;
import com.haedal.haedalweb.domain.semester.model.Semester;

public class SemesterMapper {
    public static SemesterResponseDto toDto(Semester semester) {
        return SemesterResponseDto.builder()
                .semesterId(semester.getId())
                .semesterName(semester.getName())
                .build();
    }

    public static Semester toEntity(CreateSemesterRequestDto createSemesterRequestDto) {
        return Semester.builder()
                .name(createSemesterRequestDto.getSemesterName())
                .build();
    }
}
