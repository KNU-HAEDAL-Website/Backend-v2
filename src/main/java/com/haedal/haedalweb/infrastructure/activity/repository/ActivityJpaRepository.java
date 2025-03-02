package com.haedal.haedalweb.infrastructure.activity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haedal.haedalweb.domain.activity.model.Activity;

public interface ActivityJpaRepository extends JpaRepository<Activity, Long> {
	Optional<Activity> findBySemesterIdAndId(Long semesterId, Long activityId);

	List<Activity> findBySemesterId(Long semesterId);

	boolean existsBySemesterId(Long semesterId);
}
