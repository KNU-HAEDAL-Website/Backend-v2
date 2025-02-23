package com.haedal.haedalweb.domain.activity.service;

import com.haedal.haedalweb.domain.activity.model.Activity;

public interface AdminActivityService {
	void registerActivity(Activity activity);

	void removeActivity(boolean hasRelatedBoards, Activity activity);
}
