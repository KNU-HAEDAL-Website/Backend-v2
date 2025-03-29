package com.haedal.haedalweb.domain.association.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.association.model.UserSemester;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.domain.semester.repository.SemesterRepository;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.repository.UserRepository;
import com.haedal.haedalweb.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSemesterServiceImpl implements UserSemesterService {
    private final UserRepository userRepository;
    private final SemesterRepository semesterRepository;

    @Override
    public void assignJoinedSemester(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_ID));
        Semester joinedSemester = semesterRepository.findById(user.getJoinedSemesterId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_SEMESTER_ID));

        UserSemester userSemester = UserSemester.builder()
                .user(user)
                .semester(joinedSemester)
                .build();
        user.getUserSemesters().add(userSemester);
        joinedSemester.getUserSemesters().add(userSemester);
    }
}
