package com.haedal.haedalweb.application.semester.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haedal.haedalweb.application.semester.dto.SemesterResponseDto;
import com.haedal.haedalweb.application.semester.mapper.SemesterMapper;
import com.haedal.haedalweb.domain.semester.service.SemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SemesterAppServiceImpl implements SemesterAppService {
	private final SemesterService semesterService;

	@Override
	@Transactional(readOnly = true)
	public SemesterResponseDto getSemester(Long semesterId) {
		return SemesterMapper.toDto(semesterService.getSemester(semesterId));
	}

	@Override
	@Transactional(readOnly = true)
	public List<SemesterResponseDto> getSemesters() {
		return SemesterMapper.toDtos(semesterService.getSemesters());
	}
}
