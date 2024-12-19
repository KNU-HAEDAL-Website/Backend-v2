package com.haedal.haedalweb.domain.activity.repository;

import com.haedal.haedalweb.domain.activity.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findBySemesterIdAndId(Long semesterId, Long activityId);

    List<Activity> findBySemesterId(Long semesterId);
    boolean existsBySemesterId(Long semesterId);
}
