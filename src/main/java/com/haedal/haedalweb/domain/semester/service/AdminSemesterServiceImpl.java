package com.haedal.haedalweb.domain.semester.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.domain.semester.repository.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSemesterServiceImpl implements AdminSemesterService {
    private final SemesterRepository semesterRepository;


    @Override
    public void registerSemester(Semester semester) {
        validateRegisterSemester(semester);

        semesterRepository.save(semester);
    }

    @Override 
    public void removeSemester(boolean hasRelatedActivities, Semester semester) {
        if (hasRelatedActivities) {
            throw new BusinessException(ErrorCode.EXIST_ACTIVITY);
        }

        semesterRepository.delete(semester);
    }

    private void validateRegisterSemester(Semester semester) {
        if (semesterRepository.existsByName(semester.getName())) {
            throw new BusinessException(ErrorCode.DUPLICATED_SEMESTER);
        }
    }
}
