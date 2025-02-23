package com.haedal.haedalweb.application.semester.mapper;

import java.util.List;

import com.haedal.haedalweb.application.semester.dto.SemesterRequestDto;
import com.haedal.haedalweb.application.semester.dto.SemesterResponseDto;
import com.haedal.haedalweb.domain.semester.model.Semester;

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

	public static Semester toEntity(SemesterRequestDto semesterRequestDto) {
		return Semester.builder()
			.name(semesterRequestDto.getSemesterName())
			.build();
	}
}
