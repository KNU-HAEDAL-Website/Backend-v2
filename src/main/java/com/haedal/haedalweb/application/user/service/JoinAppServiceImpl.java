package com.haedal.haedalweb.application.user.service;

import com.haedal.haedalweb.application.user.dto.EmailRequestDto;
import com.haedal.haedalweb.application.user.dto.EmailVerificationCodeRequestDto;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.domain.auth.model.CheckEmailVerification;
import com.haedal.haedalweb.domain.auth.model.EmailVerification;
import com.haedal.haedalweb.domain.auth.service.CheckEmailVerificationService;
import com.haedal.haedalweb.domain.auth.service.EmailVerificationService;
import com.haedal.haedalweb.domain.user.service.JoinService;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.infrastructure.EmailSenderService;
import com.haedal.haedalweb.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinAppServiceImpl implements JoinAppService {
    private final JoinService joinService;
    private final EmailVerificationService emailVerificationService;
    private final CheckEmailVerificationService checkEmailVerificationService;
    private final EmailSenderService emailSenderService;

    @Override
    public void checkUserIdDuplicate(String userId) {
        joinService.checkUserIdDuplicate(userId);
    }

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
                        .id(emailVerificationCodeRequestDto.getUserId())
                        .email(emailVerificationCodeRequestDto.getEmail())
                        .build()
        );
    }
}
