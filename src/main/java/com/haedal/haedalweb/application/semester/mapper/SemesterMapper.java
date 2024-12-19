package com.haedal.haedalweb.application.semester.mapper;

import com.haedal.haedalweb.application.semester.dto.CreateSemesterRequestDto;
import com.haedal.haedalweb.application.semester.dto.SemesterResponseDto;
import com.haedal.haedalweb.domain.semester.model.Semester;

import java.util.List;

public class SemesterMapper {
    private SemesterMapper() {
    }

    public static SemesterResponseDto toDto(Semester semester) {
        return SemesterResponseDto.builder()
                .semesterId(semester.getId())
                .semesterName(semester.getName())
                .build();
    }

    public static List<SemesterResponseDto> toDtos(List<Semester> semesters) {
        return semesters.stream()
                .map(SemesterMapper::toDto)
                .toList();
    }

    public static Semester toEntity(CreateSemesterRequestDto createSemesterRequestDto) {
        return Semester.builder()
                .name(createSemesterRequestDto.getSemesterName())
                .build();
    }
}
