package com.haedal.haedalweb.application.activity.service;

import com.haedal.haedalweb.domain.activity.model.Activity;

import java.util.List;

public interface ActivityAppService {
    List<Activity> getActivities(Long semesterId);
}
