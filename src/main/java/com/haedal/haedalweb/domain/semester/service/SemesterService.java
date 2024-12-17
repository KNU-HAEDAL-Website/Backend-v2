package com.haedal.haedalweb.domain.semester.service;

import com.haedal.haedalweb.domain.semester.model.Semester;
import java.util.List;

public interface SemesterService {

    List<Semester> getSemesters();
    Semester getSemester(Long semesterId);
}
