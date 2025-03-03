package com.haedal.haedalweb.domain.auth.service;

public interface EmailSenderService {
	void sendVerificationEmail(String to, String code);

	void sendRandomPassword(String to, String password);
}
