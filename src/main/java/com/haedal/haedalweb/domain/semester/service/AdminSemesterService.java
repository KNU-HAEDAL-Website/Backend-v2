package com.haedal.haedalweb.domain.semester.service;

import com.haedal.haedalweb.domain.semester.model.Semester;

public interface AdminSemesterService {

	void registerSemester(Semester semester);

	void removeSemester(boolean hasRelatedActivities, Semester semester);
}
