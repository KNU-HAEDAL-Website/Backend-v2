package com.haedal.haedalweb.application.user.service;

import com.haedal.haedalweb.application.user.dto.EmailRequestDto;
import com.haedal.haedalweb.application.user.dto.EmailVerificationCodeRequestDto;
import com.haedal.haedalweb.application.user.dto.JoinRequestDto;

public interface JoinAppService {
    void registerUserAccount(JoinRequestDto joinRequestDto);
    void registerMasterAccount(JoinRequestDto joinRequestDto); // 개발용
    void validateUserIdDuplicate(String userId);
    void validateStudentNumberDuplicate(Integer studentNumber);
    void registerAndSendEmailVerification(EmailRequestDto emailRequestDto);
    void validateCode(EmailVerificationCodeRequestDto emailVerificationCodeRequestDto);
}
