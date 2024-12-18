package com.haedal.haedalweb.domain.auth.service;

import com.haedal.haedalweb.domain.auth.model.VerifiedEmail;

public interface VerifiedEmailService {
    void registerVerifiedEmail(VerifiedEmail verifiedEmail);
    void validateVerifiedEmail(String userId, String email);
}
