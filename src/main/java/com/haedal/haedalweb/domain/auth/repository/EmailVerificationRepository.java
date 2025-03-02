package com.haedal.haedalweb.domain.auth.repository;

import java.util.Optional;

import com.haedal.haedalweb.domain.auth.model.EmailVerification;

public interface EmailVerificationRepository {
	EmailVerification save(EmailVerification emailVerification);

	void delete(EmailVerification emailVerification);

	Optional<EmailVerification> findById(String email);

	boolean existsById(String email);
}
