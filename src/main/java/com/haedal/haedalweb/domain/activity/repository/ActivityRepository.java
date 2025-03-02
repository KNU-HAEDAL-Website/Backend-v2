package com.haedal.haedalweb.domain.activity.repository;

import java.util.List;
import java.util.Optional;

import com.haedal.haedalweb.domain.activity.model.Activity;

public interface ActivityRepository {
	Optional<Activity> findById(Long activityId);

	Optional<Activity> findBySemesterIdAndId(Long semesterId, Long activityId);

	List<Activity> findBySemesterId(Long semesterId);

	boolean existsBySemesterId(Long semesterId);

	Activity save(Activity activity);

	void delete(Activity activity);
}

