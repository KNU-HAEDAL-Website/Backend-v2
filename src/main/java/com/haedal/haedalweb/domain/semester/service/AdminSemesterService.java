package com.haedal.haedalweb.domain.semester.service;


import com.haedal.haedalweb.domain.semester.model.Semester;

public interface AdminSemesterService {

    void registerSemester(String semesterName);

    void removeSemester(Semester semester, boolean hasRelatedActivities);
}
