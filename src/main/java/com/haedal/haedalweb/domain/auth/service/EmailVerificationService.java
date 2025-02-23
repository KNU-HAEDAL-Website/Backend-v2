package com.haedal.haedalweb.domain.auth.service;

import com.haedal.haedalweb.domain.auth.model.EmailVerification;

public interface EmailVerificationService {
	void registerEmailVerification(String email, String code);

	void validateCode(String email, String code);

	void deleteEmailVerification(EmailVerification emailVerification);

}
