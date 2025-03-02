package com.haedal.haedalweb.infrastructure.auth.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.auth.model.EmailVerification;
import com.haedal.haedalweb.domain.auth.repository.EmailVerificationRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryAdapter implements EmailVerificationRepository {

	private final EmailVerificationRedisRepository emailVerificationRedisRepository;

	@Override
	public EmailVerification save(EmailVerification emailVerification) {
		return emailVerificationRedisRepository.save(emailVerification);
	}

	@Override
	public void delete(EmailVerification emailVerification) {
		emailVerificationRedisRepository.delete(emailVerification);
	}

	@Override
	public Optional<EmailVerification> findById(String email) {
		return emailVerificationRedisRepository.findById(email);
	}

	@Override
	public boolean existsById(String email) {
		return emailVerificationRedisRepository.existsById(email);
	}
}
