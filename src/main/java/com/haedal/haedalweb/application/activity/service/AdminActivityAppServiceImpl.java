package com.haedal.haedalweb.application.activity.service;

import com.haedal.haedalweb.application.activity.dto.CreateActivityRequestDto;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.activity.service.ActivityService;
import com.haedal.haedalweb.domain.activity.service.AdminActivityService;
import com.haedal.haedalweb.domain.board.service.BoardService;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.domain.semester.service.SemesterService;
import com.haedal.haedalweb.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminActivityAppServiceImpl implements AdminActivityAppService {
    private final AdminActivityService adminActivityService;
    private final ActivityService activityService;
    private final SemesterService semesterService;
    private final BoardService boardService;

    @Transactional
    @Override
    public void createActivity(Long semesterId, CreateActivityRequestDto createActivityRequestDto) {
        // 학기 존재 여부 검증 및 가져오기
        Semester semester = semesterService.findSemesterById(semesterId);

        // 활동 생성
        adminActivityService.createActivity(semester, createActivityRequestDto.getActivityName());
    }

    @Transactional
    @Override
    public void deleteActivity(Long activityId) {
        // 활동 존재 여부 검증 및 가져오기
        Activity activity = activityService.findActivityById(activityId);

        // 보드와의 연관성 검증
        if (boardService.existsByActivityId(activityId)) {
            throw new BusinessException(ErrorCode.EXIST_BOARD);
        }

        // 활동 삭제
        adminActivityService.deleteActivity(activity);
    }
}
