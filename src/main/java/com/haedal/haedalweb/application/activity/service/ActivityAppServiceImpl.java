package com.haedal.haedalweb.application.activity.service;

import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityAppServiceImpl implements ActivityAppService {
    private final ActivityService activityService;

    @Override
    public List<Activity> getActivities(Long semesterId) {
        return activityService.getActivities(semesterId);
    }
}
