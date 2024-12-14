package com.haedal.haedalweb.domain.activity.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.web.activity.dto.ActivityResponseDto;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.domain.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    public List<ActivityResponseDto> getActivityDTOs(Long semesterId) {
        List<Activity> activities = activityRepository.findBySemesterId(semesterId);

        return activities.stream()
                .map((activity) -> convertToActivityDTO(activity, semesterId))
                .collect(Collectors.toList());
    }

    private ActivityResponseDto convertToActivityDTO(Activity activity, Long semesterId) {
        return ActivityResponseDto.builder()
                .activityId(activity.getId())
                .activityName(activity.getName())
                .semesterId(semesterId)
                .build();
    }

    public Activity findActivityById(Long activityId) {
        return activityRepository.findById(activityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ACTIVITY_ID));
    }
}
