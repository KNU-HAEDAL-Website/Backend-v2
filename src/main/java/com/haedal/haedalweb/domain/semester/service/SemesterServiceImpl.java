package com.haedal.haedalweb.domain.semester.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.domain.semester.repository.SemesterRepository;
import com.haedal.haedalweb.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SemesterServiceImpl implements SemesterService {
	private final SemesterRepository semesterRepository;

	@Override
	public Semester getSemester(Long semesterId) {
		return semesterRepository.findById(semesterId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_SEMESTER_ID));
	}

	@Override
	public List<Semester> getSemesters() {
		return semesterRepository.findAll(Sort.by("name"));
	}
}
