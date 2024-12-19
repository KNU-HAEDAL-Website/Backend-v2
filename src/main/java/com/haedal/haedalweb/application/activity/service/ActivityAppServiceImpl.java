package com.haedal.haedalweb.application.activity.service;

import com.haedal.haedalweb.application.activity.dto.ActivityResponseDto;
import com.haedal.haedalweb.application.activity.mapper.ActivityMapper;
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
    public List<ActivityResponseDto> getActivities(Long semesterId) {
        List<Activity> activities = activityService.getActivities(semesterId);

        return ActivityMapper.toDtos(activities);
    }
}
