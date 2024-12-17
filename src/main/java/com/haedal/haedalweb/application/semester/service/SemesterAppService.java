package com.haedal.haedalweb.application.semester.service;

import com.haedal.haedalweb.domain.semester.model.Semester;
import java.util.List;

public interface SemesterAppService {
    List<Semester> getSemesters();
    Semester getSemester(Long semesterId);
}
