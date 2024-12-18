package com.haedal.haedalweb.application.semester.service;

import java.util.List;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.domain.semester.service.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class SemesterAppServiceImpl implements SemesterAppService {
    private final SemesterService semesterService;

    @Override
    @Transactional(readOnly = true)
    public Semester getSemester(Long semesterId) {
        return semesterService.getSemester(semesterId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Semester> getSemesters() {
        return semesterService.getSemesters();
    }
}
