package com.haedal.haedalweb.domain.auth.service;

import com.haedal.haedalweb.domain.auth.model.CheckEmailVerification;

public interface CheckEmailVerificationService {
    void saveCheckEmailVerification(CheckEmailVerification checkEmailVerification);
    void validateCertifiedEmail(String userId, String email);
}
