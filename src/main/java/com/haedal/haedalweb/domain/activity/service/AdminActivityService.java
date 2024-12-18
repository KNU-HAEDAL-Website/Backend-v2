package com.haedal.haedalweb.domain.activity.service;

import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.semester.model.Semester;

public interface AdminActivityService {
    Activity registerActivity(Semester semester, String activityName);

    void removeActivity(Activity activity, boolean hasRelatedBoards);
}
