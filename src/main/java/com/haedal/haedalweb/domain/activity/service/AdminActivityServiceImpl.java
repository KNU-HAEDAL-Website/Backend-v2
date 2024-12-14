package com.haedal.haedalweb.domain.activity.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.web.activity.dto.CreateActivityRequestDto;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.domain.activity.repository.ActivityRepository;
import com.haedal.haedalweb.domain.board.repository.BoardRepository;
import com.haedal.haedalweb.domain.semester.repository.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminActivityServiceImpl implements AdminActivityService {
    private final SemesterRepository semesterRepository;
    private final ActivityRepository activityRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void createActivity(Long semesterId, CreateActivityRequestDto createActivityRequestDto) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_SEMESTER_ID));

        Activity activity = Activity.builder()
                .name(createActivityRequestDto.getActivityName())
                .semester(semester)
                .build();

        activityRepository.save(activity);
    }

    @Transactional
    public void deleteActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ACTIVITY_ID));

        validateDeleteActivityRequest(activityId);
        activityRepository.delete(activity);
    }

    private void validateDeleteActivityRequest(Long activityId) {
        if (boardRepository.existsByActivityId(activityId)) {
            throw new BusinessException(ErrorCode.EXIST_BOARD);
        }
    }
}
