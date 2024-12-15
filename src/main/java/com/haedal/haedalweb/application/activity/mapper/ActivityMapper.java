package com.haedal.haedalweb.application.activity.mapper;

import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.application.activity.dto.ActivityResponseDto;

public interface ActivityMapper {
    ActivityResponseDto toDto(Activity activity);
    Activity toEntity(ActivityResponseDto dto);
}
