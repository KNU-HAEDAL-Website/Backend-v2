package com.haedal.haedalweb.application.activity.service;

import com.haedal.haedalweb.application.activity.dto.ActivityRequestDto;

public interface AdminActivityAppService {
    void registerActivity(Long semesterId, ActivityRequestDto activityRequestDto);
    void removeActivity(Long semesterId, Long activityId);
}
