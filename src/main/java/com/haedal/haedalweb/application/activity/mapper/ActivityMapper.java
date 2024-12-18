package com.haedal.haedalweb.application.activity.mapper;

import com.haedal.haedalweb.application.activity.dto.CreateActivityRequestDto;
import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.application.activity.dto.ActivityResponseDto;
import com.haedal.haedalweb.domain.semester.model.Semester;

public class ActivityMapper {
    public static ActivityResponseDto toDto(Activity activity) {
        return ActivityResponseDto.builder()
                .activityId(activity.getId())
                .activityName(activity.getName())
                .semesterId(activity.getSemester().getId())
                .build();
    }

    public static Activity toEntity(Semester semester, CreateActivityRequestDto createActivityRequestDto) {
        return Activity.builder()
                .name(createActivityRequestDto.getActivityName())
                .semester(semester)
                .build();
    }
}