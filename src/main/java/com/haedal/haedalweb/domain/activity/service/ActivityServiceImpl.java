package com.haedal.haedalweb.domain.activity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.activity.repository.ActivityRepository;
import com.haedal.haedalweb.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {
	private final ActivityRepository activityRepository;

	@Override
	public Activity getActivity(Long activityId) {
		return activityRepository.findById(activityId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ACTIVITY_ID));
	}

	@Override
	public Activity getActivity(Long semesterId, Long activityId) {
		return activityRepository.findBySemesterIdAndId(semesterId, activityId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ACTIVITY_ID));
	}

	@Override
	public List<Activity> getActivities(Long semesterId) {
		return activityRepository.findBySemesterId(semesterId);
	}

	@Override
	public boolean hasActivitiesBySemesterId(Long semesterId) {
		return activityRepository.existsBySemesterId(semesterId);
	}
}
