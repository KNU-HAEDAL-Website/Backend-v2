package com.haedal.haedalweb.domain.auth.repository;

import java.util.Optional;

import com.haedal.haedalweb.domain.auth.model.VerifiedEmail;

public interface VerifiedEmailRepository {
	VerifiedEmail save(VerifiedEmail verifiedEmail);

	Optional<VerifiedEmail> findById(String userId);
}
