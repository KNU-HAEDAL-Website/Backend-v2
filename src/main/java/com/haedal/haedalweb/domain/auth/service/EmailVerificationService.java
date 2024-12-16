package com.haedal.haedalweb.domain.auth.service;

import com.haedal.haedalweb.domain.auth.model.EmailVerification;

public interface EmailVerificationService {
    void saveEmailVerification(EmailVerification emailVerification);

    EmailVerification findEmailVerificationById(String email);

    void deleteEmailVerification(EmailVerification emailVerification);

}
