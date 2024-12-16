package com.haedal.haedalweb.application.user.service;

import com.haedal.haedalweb.application.user.dto.EmailRequestDto;
import com.haedal.haedalweb.domain.auth.service.EmailVerificationService;
import com.haedal.haedalweb.domain.user.service.JoinService;
import com.haedal.haedalweb.infrastructure.EmailSenderService;
import com.haedal.haedalweb.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinAppServiceImpl implements JoinAppService {
    private final JoinService joinService;
    private final EmailVerificationService emailVerificationService;
    private final EmailSenderService emailSenderService;

    public void createAndSendVerificationCode(EmailRequestDto emailRequestDto) {
        emailSenderService.sendVerificationEmail(emailRequestDto.getEmail(), EmailUtil.generateVerificationCode());
    }
}
