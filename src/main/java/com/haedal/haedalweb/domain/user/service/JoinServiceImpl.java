package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.profile.model.Profile;
import com.haedal.haedalweb.domain.sns.model.Sns;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {
    private final UserRepository userRepository;

    @Override
    public void createAccount(User user) {
        validateCreateUser(user);
        userRepository.save(user);
    }

    @Override
    public void checkUserIdDuplicate(String userId) {
        if (userRepository.existsById(userId)) {
            throw new BusinessException(ErrorCode.DUPLICATED_USER_ID);
        }
    }

    @Override
    public void checkStudentNumberDuplicate(Integer studentNumber) {
        if (userRepository.existsByStudentNumber(studentNumber)) {
            throw new BusinessException(ErrorCode.DUPLICATED_STUDENT_NUMBER);
        }
    }

    @Override
    public void checkEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    private void validateCreateUser(User user) {
        checkUserIdDuplicate(user.getId());
        checkStudentNumberDuplicate(user.getStudentNumber());
        checkEmailDuplicate(user.getEmail());

    }

    private Profile createProfileWithSns() {
        Sns sns = Sns.builder().build();

        return Profile.builder()
                .sns(sns)
                .build();
    }
}
