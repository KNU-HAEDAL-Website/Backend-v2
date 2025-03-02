package com.haedal.haedalweb.infrastructure.activity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.activity.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ActivityRepositoryAdapter implements ActivityRepository {

	private final ActivityJpaRepository activityJpaRepository;

	@Override
	public Optional<Activity> findById(Long activityId) {
		return activityJpaRepository.findById(activityId);
	}

	@Override
	public Optional<Activity> findBySemesterIdAndId(Long semesterId, Long activityId) {
		return activityJpaRepository.findBySemesterIdAndId(semesterId, activityId);
	}

	@Override
	public List<Activity> findBySemesterId(Long semesterId) {
		return activityJpaRepository.findBySemesterId(semesterId);
	}

	@Override
	public boolean existsBySemesterId(Long semesterId) {
		return activityJpaRepository.existsBySemesterId(semesterId);
	}

	@Override
	public Activity save(Activity activity) {
		return activityJpaRepository.save(activity);
	}

	@Override
	public void delete(Activity activity) {
		activityJpaRepository.delete(activity);
	}
}
