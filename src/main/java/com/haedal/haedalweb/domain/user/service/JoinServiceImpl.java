package com.haedal.haedalweb.domain.user.service;


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
    public void registerAccount(User user) {
        validateRegisterUser(user);
        userRepository.save(user);
    }

    @Override
    public void validateUserId(String userId) {
        if (userRepository.existsById(userId)) {
            throw new BusinessException(ErrorCode.DUPLICATED_USER_ID);
        }
    }

    @Override
    public void validateStudentNumber(Integer studentNumber) {
        if (userRepository.existsByStudentNumber(studentNumber)) {
            throw new BusinessException(ErrorCode.DUPLICATED_STUDENT_NUMBER);
        }
    }

    @Override
    public void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    private void validateRegisterUser(User user) {
        validateUserId(user.getId());
        validateStudentNumber(user.getStudentNumber());
        validateEmail(user.getEmail());
    }
}
