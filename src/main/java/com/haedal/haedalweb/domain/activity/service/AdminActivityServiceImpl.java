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
    public void registerActivity(Activity activity) {
        activityRepository.save(activity);
    }

    @Override
    public void removeActivity(boolean hasRelatedBoards, Activity activity) {
        if (hasRelatedBoards) {
            throw new BusinessException(ErrorCode.EXIST_BOARD);
        }

        activityRepository.delete(activity);
    }
}
