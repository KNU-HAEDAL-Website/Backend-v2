package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.profile.model.Profile;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.sns.model.Sns;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.application.user.dto.JoinRequestDto;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUserAccount(JoinRequestDto joinRequestDTO) {
        String userId = joinRequestDTO.getUserId();
        String password = joinRequestDTO.getPassword();
        Integer studentNumber = joinRequestDTO.getStudentNumber();
        String userName = joinRequestDTO.getUserName();

        validateJoinRequest(joinRequestDTO);

        User user = User.builder()
                .id(userId)
                .password(passwordEncoder.encode(password))
                .name(userName)
                .studentNumber(studentNumber)
                .role(Role.ROLE_MEMBER)
                .userStatus(UserStatus.INACTIVE)
                .regDate(LocalDateTime.now())
                //.profile(createProfileWithSns())
                .build();

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void createAdminAccount(JoinRequestDto joinRequestDTO) { // 관리자 회원가입 (개발용)
        String userId = joinRequestDTO.getUserId();
        String password = joinRequestDTO.getPassword();
        Integer studentNumber = joinRequestDTO.getStudentNumber();
        String userName = joinRequestDTO.getUserName();

        validateJoinRequest(joinRequestDTO);

        User user = User.builder()
                .id(userId)
                .password(passwordEncoder.encode(password))
                .name(userName)
                .studentNumber(studentNumber)
                .role(Role.ROLE_ADMIN)
                .userStatus(UserStatus.ACTIVE)
                .regDate(LocalDateTime.now())
                //.profile(createProfileWithSns())
                .build();

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

    private void validateJoinRequest(JoinRequestDto joinRequestDTO) {
        if (isUserIdDuplicate(joinRequestDTO.getUserId())) {
            throw new BusinessException(ErrorCode.DUPLICATED_USER_ID);
        }

        if (isStudentNumberDuplicate(joinRequestDTO.getStudentNumber())) {
            throw new BusinessException(ErrorCode.DUPLICATED_STUDENT_NUMBER);
        }
    }

    private Profile createProfileWithSns() {
        Sns sns = Sns.builder().build();

        return Profile.builder()
                .sns(sns)
                .build();
    }
}
