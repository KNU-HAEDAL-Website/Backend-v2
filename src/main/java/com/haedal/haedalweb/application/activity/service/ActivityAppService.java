package com.haedal.haedalweb.application.activity.service;

import java.util.List;

import com.haedal.haedalweb.application.activity.dto.ActivityResponseDto;

public interface ActivityAppService {
	List<ActivityResponseDto> getActivities(Long semesterId);

	ActivityResponseDto getActivity(Long semesterId, Long activityId);
}
