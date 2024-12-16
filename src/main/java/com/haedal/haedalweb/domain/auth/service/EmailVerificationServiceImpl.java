package com.haedal.haedalweb.domain.auth.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.auth.model.EmailVerification;
import com.haedal.haedalweb.domain.auth.repository.EmailVerificationRepository;
import com.haedal.haedalweb.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {
    public final EmailVerificationRepository emailVerificationRepository;

    @Override
    public void saveEmailVerification(EmailVerification emailVerification) {
        emailVerificationRepository.save(emailVerification);
    }

    @Override
    public EmailVerification findEmailVerificationById(String email) {
        return emailVerificationRepository.findById(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_EMAIL_VERIFICATION));
    }

    @Override
    public void deleteEmailVerification(EmailVerification emailVerification) {
        emailVerificationRepository.delete(emailVerification);
    }
}
