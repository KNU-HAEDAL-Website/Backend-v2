package com.haedal.haedalweb.domain.activity.service;

import com.haedal.haedalweb.web.activity.dto.CreateActivityRequestDto;

/**
 * Interface for administering activities within a semester.
 */
public interface AdminActivityService {
    void createActivity(Long semesterId, CreateActivityRequestDto createActivityRequestDto);

    void deleteActivity(Long activityId);
}
