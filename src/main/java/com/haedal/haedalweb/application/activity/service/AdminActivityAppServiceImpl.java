package com.haedal.haedalweb.application.activity.service;

import com.haedal.haedalweb.application.activity.dto.ActivityRequestDto;
import com.haedal.haedalweb.application.activity.mapper.ActivityMapper;
import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.activity.service.ActivityService;
import com.haedal.haedalweb.domain.activity.service.AdminActivityService;
import com.haedal.haedalweb.domain.board.service.BoardService;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.domain.semester.service.SemesterService;
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
    public void registerActivity(Long semesterId, ActivityRequestDto activityRequestDto) {
        // 학기 존재 여부 검증 및 가져오기
        Semester semester = semesterService.getSemester(semesterId);

        // 활동 생성
        Activity activity = Activity.builder()
                .name(activityRequestDto.getActivityName())
                .semester(semester)
                .build();

        // 활동 저장
        adminActivityService.registerActivity(activity);
    }

    @Transactional
    @Override
    public void removeActivity(Long semesterId, Long activityId) {
        // 활동 존재 여부 검증 및 가져오기
        Activity activity = activityService.getActivity(semesterId, activityId);

        // 보드와의 연관성 검증
        boolean hasRelatedBoards = boardService.hasBoardsByActivityId(activityId);

        // 활동 삭제 요청
        adminActivityService.removeActivity(hasRelatedBoards, activity);
    }
}
