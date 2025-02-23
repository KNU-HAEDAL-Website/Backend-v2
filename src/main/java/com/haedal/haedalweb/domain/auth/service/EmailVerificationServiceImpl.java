package com.haedal.haedalweb.domain.auth.service;

import org.springframework.stereotype.Service;

import com.haedal.haedalweb.constants.EmailConstants;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.auth.model.EmailVerification;
import com.haedal.haedalweb.domain.auth.repository.EmailVerificationRepository;
import com.haedal.haedalweb.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {
	public final EmailVerificationRepository emailVerificationRepository;

	@Override
	public void registerEmailVerification(String email, String code) {
		if (checkEmailVerificationExists(email)) {
			EmailVerification emailVerification = getEmailVerification(email);

			if (emailVerification.getResendCount() >= EmailConstants.RESEND_LIMIT) {
				throw new BusinessException(ErrorCode.LIMIT_EXCEEDED_SEND_EMAIL);
			}

			emailVerification.setCode(code);
			emailVerification.setResendCount(emailVerification.getResendCount() + 1);
			emailVerificationRepository.save(emailVerification);
			return;
		}

		EmailVerification emailVerification = EmailVerification.builder()
			.email(email)
			.code(code)
			.resendCount(1)
			.build();

		emailVerificationRepository.save(emailVerification);
	}

	@Override
	public void validateCode(String email, String code) {
		EmailVerification emailVerification = getEmailVerification(email);

		if (!emailVerification.getCode().equals(code)) {
			throw new BusinessException(ErrorCode.INVALID_EMAIL_VERIFICATION);
		}
	}

	@Override
	public void deleteEmailVerification(EmailVerification emailVerification) {
		emailVerificationRepository.delete(emailVerification);
	}

	private EmailVerification getEmailVerification(String email) {
		return emailVerificationRepository.findById(email)
			.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_EMAIL_VERIFICATION));
	}

	private boolean checkEmailVerificationExists(String email) {
		return emailVerificationRepository.existsById(email);
	}
}
