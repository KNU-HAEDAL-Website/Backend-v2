package com.haedal.haedalweb.domain.activity.service;

import com.haedal.haedalweb.domain.activity.model.Activity;
import java.util.List;

public interface ActivityService {
    Activity findActivityById(Long activityId);
    List<Activity> getActivitiesBySemesterId(Long semesterId);
}