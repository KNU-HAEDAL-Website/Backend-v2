package com.haedal.haedalweb.application.activity.mapper;

import java.util.List;

import com.haedal.haedalweb.application.activity.dto.ActivityResponseDto;
import com.haedal.haedalweb.domain.activity.model.Activity;

public class ActivityMapper {
	private ActivityMapper() {
	}

	public static ActivityResponseDto toDto(Activity activity) {
		return ActivityResponseDto.builder()
			.activityId(activity.getId())
			.activityName(activity.getName())
			.semesterId(activity.getSemester().getId())
			.build();
	}

	public static List<ActivityResponseDto> toDtos(List<Activity> activities) {
		return activities.stream()
			.map(ActivityMapper::toDto)
			.toList();
	}
}