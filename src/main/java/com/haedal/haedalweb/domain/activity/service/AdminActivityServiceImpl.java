package com.haedal.haedalweb.domain.activity.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.domain.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminActivityServiceImpl implements AdminActivityService {
//    private final SemesterRepository semesterRepository;
    private final ActivityRepository activityRepository;
//    private final BoardRepository boardRepository;

//    @Transactional
//    @Override
//    public void createActivity(Long semesterId, CreateActivityRequestDto createActivityRequestDto) {
//        Semester semester = semesterRepository.findById(semesterId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_SEMESTER_ID));
//
//        Activity activity = Activity.builder()
//                .name(createActivityRequestDto.getActivityName())
//                .semester(semester)
//                .build();
//
//        activityRepository.save(activity);
//    }

    @Override
    public Activity createActivity(Semester semester, String activityName) {
        Activity activity = Activity.builder()
                .name(activityName)
                .semester(semester)
                .build();

        return activityRepository.save(activity);
    }

    @Override
    public void deleteActivity(Activity activity) {
//        Activity activity = activityRepository.findById(activityId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ACTIVITY_ID));

//        validateDeleteActivityRequest(activityId);
        activityRepository.delete(activity);
    }

//    private void validateDeleteActivityRequest(Long activityId) {
//        if (boardRepository.existsByActivityId(activityId)) {
//            throw new BusinessException(ErrorCode.EXIST_BOARD);
//        }
//    }
}
