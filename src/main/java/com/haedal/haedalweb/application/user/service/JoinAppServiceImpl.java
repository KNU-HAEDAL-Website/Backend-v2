package com.haedal.haedalweb.application.user.service;

import com.haedal.haedalweb.application.user.dto.EmailRequestDto;
import com.haedal.haedalweb.application.user.dto.EmailVerificationCodeRequestDto;
import com.haedal.haedalweb.application.user.dto.JoinRequestDto;
import com.haedal.haedalweb.domain.auth.model.CheckEmailVerification;
import com.haedal.haedalweb.domain.auth.service.CheckEmailVerificationService;
import com.haedal.haedalweb.domain.auth.service.EmailVerificationService;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.domain.user.service.JoinService;
import com.haedal.haedalweb.infrastructure.EmailSenderService;
import com.haedal.haedalweb.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinAppServiceImpl implements JoinAppService {
    private final JoinService joinService;
    private final EmailVerificationService emailVerificationService;
    private final CheckEmailVerificationService checkEmailVerificationService;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void createUserAccount(JoinRequestDto joinRequestDto) {
        // 인증된 이메일인지 확인
        checkEmailVerificationService.validateCertifiedEmail(joinRequestDto.getUserId(), joinRequestDto.getEmail());

        // 일반 멤버로 설정
        User user = createUserFromDto(joinRequestDto, Role.ROLE_MEMBER, UserStatus.INACTIVE);

        // 등록
        joinService.createAccount(user);
    }

    @Transactional
    @Override
    public void createMasterAccount(JoinRequestDto joinRequestDto) { // 개발용
        // 인증된 이메일인지 확인
        checkEmailVerificationService.validateCertifiedEmail(joinRequestDto.getUserId(), joinRequestDto.getEmail());

        // 웹 관리자로 설정
        User user = createUserFromDto(joinRequestDto, Role.ROLE_WEB_MASTER, UserStatus.MASTER);

        // 등록
        joinService.createAccount(user);
    }

    @Transactional(readOnly = true)
    @Override
    public void checkUserIdDuplicate(String userId) {
        joinService.checkUserIdDuplicate(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public void checkStudentNumberDuplicate(Integer studentNumber) {
        joinService.checkStudentNumberDuplicate(studentNumber);
    }

    @Transactional
    @Override
    public void createAndSendVerificationCode(EmailRequestDto emailRequestDto) {
        String email = emailRequestDto.getEmail();

        // 이메일 중복 검사
        joinService.checkEmailDuplicate(email);

        // 인증 코드 생성 후 저장
        String code = EmailUtil.generateVerificationCode();
        emailVerificationService.saveEmailVerification(email, code);

        // 인증 코드 전송
        emailSenderService.sendVerificationEmail(email, code);
    }

    @Transactional
    @Override
    public void verifyCode(EmailVerificationCodeRequestDto emailVerificationCodeRequestDto) {
        // 인증 코드 검증
        emailVerificationService.verifyCode(emailVerificationCodeRequestDto.getEmail(), emailVerificationCodeRequestDto.getCode());

        // 인증 여부 저장 (특정 ID로 특정 Email 인증 여부)
        checkEmailVerificationService.saveCheckEmailVerification(
                CheckEmailVerification.builder()
                        .userId(emailVerificationCodeRequestDto.getUserId())
                        .email(emailVerificationCodeRequestDto.getEmail())
                        .build()
        );
    }

    private User createUserFromDto(JoinRequestDto dto, Role role, UserStatus userStatus) {
        return User.builder()
                .id(dto.getUserId())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getUserName())
                .role(role)
                .userStatus(userStatus)
                .studentNumber(dto.getStudentNumber())
                .build();
    }
}
