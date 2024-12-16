package com.haedal.haedalweb.application.user.service;

import com.haedal.haedalweb.application.user.dto.EmailRequestDto;
import com.haedal.haedalweb.application.user.dto.EmailVerificationCodeRequestDto;

public interface JoinAppService {
    void checkUserIdDuplicate(String userId);
    void checkStudentNumberDuplicate(Integer studentNumber);
    void createAndSendVerificationCode(EmailRequestDto emailRequestDto);
    void verifyCode(EmailVerificationCodeRequestDto emailVerificationCodeRequestDto);
}
