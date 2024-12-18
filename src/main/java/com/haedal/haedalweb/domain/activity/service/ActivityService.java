package com.haedal.haedalweb.domain.activity.service;

import com.haedal.haedalweb.domain.activity.model.Activity;
import java.util.List;

public interface ActivityService {
    Activity getActivity(Long activityId);
    List<Activity> getActivities(Long semesterId);
    boolean hasActivitiesBySemesterId(Long semesterId);
}
