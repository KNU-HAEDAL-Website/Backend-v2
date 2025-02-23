package com.haedal.haedalweb.domain.activity.service;

import java.util.List;

import com.haedal.haedalweb.domain.activity.model.Activity;

public interface ActivityService {
	Activity getActivity(Long semesterId, Long activityId);

	Activity getActivity(Long activityId);

	List<Activity> getActivities(Long semesterId);

	boolean hasActivitiesBySemesterId(Long semesterId);
}
