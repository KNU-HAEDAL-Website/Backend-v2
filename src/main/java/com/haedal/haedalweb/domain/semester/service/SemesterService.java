package com.haedal.haedalweb.domain.semester.service;

import java.util.List;

import com.haedal.haedalweb.domain.semester.model.Semester;

public interface SemesterService {

	List<Semester> getSemesters();

	Semester getSemester(Long semesterId);
}
