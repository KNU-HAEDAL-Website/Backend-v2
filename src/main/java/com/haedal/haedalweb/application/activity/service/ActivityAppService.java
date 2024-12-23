package com.haedal.haedalweb.application.activity.service;

import com.haedal.haedalweb.application.activity.dto.ActivityResponseDto;

import java.util.List;

public interface ActivityAppService {
    List<ActivityResponseDto> getActivities(Long semesterId);

    ActivityResponseDto getActivity(Long semesterId, Long activityId);
}
