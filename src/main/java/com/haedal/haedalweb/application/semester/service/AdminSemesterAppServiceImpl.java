package com.haedal.haedalweb.application.semester.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haedal.haedalweb.application.semester.dto.SemesterRequestDto;
import com.haedal.haedalweb.application.semester.mapper.SemesterMapper;
import com.haedal.haedalweb.domain.activity.service.ActivityService;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.domain.semester.service.AdminSemesterService;
import com.haedal.haedalweb.domain.semester.service.SemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminSemesterAppServiceImpl implements AdminSemesterAppService {
	private final AdminSemesterService adminSemesterService;
	private final SemesterService semesterService;
	private final ActivityService activityService;

	@Transactional
	@Override
	public void registerSemester(SemesterRequestDto semesterRequestDto) {
		Semester semester = SemesterMapper.toEntity(semesterRequestDto);

		adminSemesterService.registerSemester(semester);
	}

	@Transactional
	@Override
	public void removeSemester(Long semesterId) {
		// 학기 존재 여부 검증 및 가져오기
		Semester semester = semesterService.getSemester(semesterId);

		// 활동과의 연관성 검증
		boolean hasRelatedActivities = activityService.hasActivitiesBySemesterId(semesterId);

		// 학기 삭제
		adminSemesterService.removeSemester(hasRelatedActivities, semester);
	}
}
