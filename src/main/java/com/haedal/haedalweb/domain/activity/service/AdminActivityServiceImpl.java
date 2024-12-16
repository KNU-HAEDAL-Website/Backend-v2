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
    private final ActivityRepository activityRepository;

    @Override
    public Activity createActivity(Semester semester, String activityName) {
        Activity activity = Activity.builder()
                .name(activityName)
                .semester(semester)
                .build();

        return activityRepository.save(activity);
    }

    @Override
    public void deleteActivity(Activity activity, boolean hasRelatedBoards) {
        if (hasRelatedBoards) {
            throw new BusinessException(ErrorCode.EXIST_BOARD);
        }

        activityRepository.delete(activity);
    }
}
