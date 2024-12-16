package com.haedal.haedalweb.domain.auth.service;

import com.haedal.haedalweb.constants.EmailConstants;
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
    public void saveEmailVerification(String email, String code) {
        if (existsById(email)) {
            EmailVerification emailVerification = findEmailVerification(email);

            if (emailVerification.getResendCount() >= EmailConstants.RESEND_LIMIT) {
                throw new BusinessException(ErrorCode.LIMIT_EXCEEDED_SEND_EMAIL);
            }

            emailVerification.setCode(code);
            emailVerification.setResendCount(emailVerification.getResendCount()+1);
            emailVerificationRepository.save(emailVerification);
            return;
        }

        EmailVerification emailVerification = EmailVerification.builder()
                .email(email)
                .code(code)
                .resendCount(0)
                .build();

        emailVerificationRepository.save(emailVerification);
    }

    @Override
    public void verifyCode(String email, String code) {
        EmailVerification emailVerification = findEmailVerification(email);

        if (!emailVerification.getCode().equals(code)) {
            throw new BusinessException(ErrorCode.INVALID_EMAIL_VERIFICATION);
        }
    }

    @Override
    public void deleteEmailVerification(EmailVerification emailVerification) {
        emailVerificationRepository.delete(emailVerification);
    }

    private EmailVerification findEmailVerification(String email) {
        return emailVerificationRepository.findById(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_EMAIL_VERIFICATION));
    }

    private boolean existsById(String email) {
        return emailVerificationRepository.existsById(email);
    }
}
