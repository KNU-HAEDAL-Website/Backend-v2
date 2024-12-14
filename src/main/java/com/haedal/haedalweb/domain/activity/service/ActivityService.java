package com.haedal.haedalweb.domain.activity.service;

import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.web.activity.dto.ActivityResponseDto;
import java.util.List;

public interface ActivityService {

    List<ActivityResponseDto> getActivityDTOs(Long semesterId);
    Activity findActivityById(Long activityId);
}
