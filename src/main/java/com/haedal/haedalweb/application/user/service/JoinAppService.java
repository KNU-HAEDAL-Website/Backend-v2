package com.haedal.haedalweb.application.user.service;

import com.haedal.haedalweb.application.user.dto.EmailRequestDto;

public interface JoinAppService {
    void createAndSendVerificationCode(EmailRequestDto emailRequestDto);
}
