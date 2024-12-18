package com.haedal.haedalweb.application.activity.service;

import com.haedal.haedalweb.application.activity.dto.CreateActivityRequestDto;

public interface AdminActivityAppService {
    void registerActivity(Long semesterId, CreateActivityRequestDto createActivityRequestDto);
    void removeActivity(Long activityId);
}
