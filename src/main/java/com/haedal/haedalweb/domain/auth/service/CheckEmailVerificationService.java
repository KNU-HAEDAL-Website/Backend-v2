package com.haedal.haedalweb.domain.auth.service;

import com.haedal.haedalweb.domain.auth.model.CheckEmailVerification;

public interface CheckEmailVerificationService {
    void saveCheckEmailVerification(CheckEmailVerification checkEmailVerification);
    CheckEmailVerification findCheckEmailVerification(String id);
}
