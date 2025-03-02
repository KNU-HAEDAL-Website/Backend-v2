package com.haedal.haedalweb.infrastructure.auth.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.auth.model.VerifiedEmail;
import com.haedal.haedalweb.domain.auth.repository.VerifiedEmailRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VerifiedEmailRepositoryAdapter implements VerifiedEmailRepository {

	private final VerifiedEmailRedisRepository verifiedEmailRedisRepository;

	@Override
	public VerifiedEmail save(VerifiedEmail verifiedEmail) {
		return verifiedEmailRedisRepository.save(verifiedEmail);
	}

	@Override
	public Optional<VerifiedEmail> findById(String userId) {
		return verifiedEmailRedisRepository.findById(userId);
	}
}
