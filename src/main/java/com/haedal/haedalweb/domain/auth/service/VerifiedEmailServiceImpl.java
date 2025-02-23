package com.haedal.haedalweb.domain.auth.service;

import org.springframework.stereotype.Service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.auth.model.VerifiedEmail;
import com.haedal.haedalweb.domain.auth.repository.VerifiedEmailRepository;
import com.haedal.haedalweb.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifiedEmailServiceImpl implements VerifiedEmailService {
	private final VerifiedEmailRepository verifiedEmailRepository;

	@Override
	public void registerVerifiedEmail(VerifiedEmail verifiedEmail) {
		verifiedEmailRepository.save(verifiedEmail);
	}

	@Override
	public void validateVerifiedEmail(String userId, String email) {
		VerifiedEmail verifiedEmail = verifiedEmailRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CHECK_EMAIL_VERIFICATION));

		if (!verifiedEmail.getEmail().equals(email)) {
			throw new BusinessException(ErrorCode.NOT_FOUND_CHECK_EMAIL_VERIFICATION);
		}
	}
}
