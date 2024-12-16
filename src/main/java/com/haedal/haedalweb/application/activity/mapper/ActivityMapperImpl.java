package com.haedal.haedalweb.application.activity.mapper;

import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.application.activity.dto.ActivityResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapperImpl implements ActivityMapper {
    @Override
    public ActivityResponseDto toDto(Activity activity) {
        return ActivityResponseDto.builder()
                .activityId(activity.getId())
                .activityName(activity.getName())
                .semesterId(activity.getSemester().getId())
                .build();
    }
}
