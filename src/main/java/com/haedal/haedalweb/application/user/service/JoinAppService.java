package com.haedal.haedalweb.application.user.service;

import com.haedal.haedalweb.application.user.dto.EmailRequestDto;
import com.haedal.haedalweb.application.user.dto.EmailVerificationCodeRequestDto;
import com.haedal.haedalweb.application.user.dto.JoinRequestDto;
import com.haedal.haedalweb.domain.user.model.User;

public interface JoinAppService {
    void createUserAccount(JoinRequestDto joinRequestDto);
    void createMasterAccount(JoinRequestDto joinRequestDto); // 개발용
    void checkUserIdDuplicate(String userId);
    void checkStudentNumberDuplicate(Integer studentNumber);
    void createAndSendVerificationCode(EmailRequestDto emailRequestDto);
    void verifyCode(EmailVerificationCodeRequestDto emailVerificationCodeRequestDto);
}
