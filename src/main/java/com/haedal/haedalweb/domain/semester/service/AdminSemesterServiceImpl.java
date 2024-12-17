package com.haedal.haedalweb.domain.semester.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.application.semester.dto.CreateSemesterRequestDto;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.domain.activity.repository.ActivityRepository;
import com.haedal.haedalweb.domain.semester.repository.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSemesterServiceImpl implements AdminSemesterService {
    private final SemesterRepository semesterRepository;
    private final ActivityRepository activityRepository;

    @Transactional
    public void createSemester(CreateSemesterRequestDto createSemesterRequestDto) {
        validateAddSemesterRequest(createSemesterRequestDto);

        String semesterName = createSemesterRequestDto.getSemesterName();
        Semester semester = Semester.builder()
                .name(semesterName)
                .build();

        semesterRepository.save(semester);
    }

    @Transactional
    public void deleteSemester(Long semesterId) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_SEMESTER_ID));

        validateDeleteSemesterRequest(semesterId);

        semesterRepository.delete(semester);
    }

    private void validateAddSemesterRequest(CreateSemesterRequestDto createSemesterRequestDto) {
        if (isSemesterNameDuplicate(createSemesterRequestDto.getSemesterName())) {
            throw new BusinessException(ErrorCode.DUPLICATED_SEMESTER);
        }
    }

    private void validateDeleteSemesterRequest(Long semesterId) {
        if (activityRepository.existsBySemesterId(semesterId)) {
            throw new BusinessException(ErrorCode.EXIST_ACTIVITY);
        }
    }


    private boolean isSemesterNameDuplicate(String semesterName) {
        return semesterRepository.existsByName(semesterName);
    }
}
