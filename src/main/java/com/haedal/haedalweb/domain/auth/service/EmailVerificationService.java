package com.haedal.haedalweb.domain.auth.service;

import com.haedal.haedalweb.domain.auth.model.EmailVerification;

public interface EmailVerificationService {
    void saveEmailVerification(String email, String code);

    void verifyCode(String email, String code);

    void deleteEmailVerification(EmailVerification emailVerification);

}
