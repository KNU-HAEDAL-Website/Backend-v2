package com.haedal.haedalweb.infrastructure.email;

public interface EmailSenderService {
	void sendVerificationEmail(String to, String code);
}
