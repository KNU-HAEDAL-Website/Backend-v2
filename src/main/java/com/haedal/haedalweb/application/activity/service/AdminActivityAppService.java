package com.haedal.haedalweb.application.activity.service;

import com.haedal.haedalweb.application.activity.dto.CreateActivityRequestDto;

public interface AdminActivityAppService {
    void createActivity(Long semesterId, CreateActivityRequestDto createActivityRequestDto);
    void deleteActivity(Long activityId);
}
