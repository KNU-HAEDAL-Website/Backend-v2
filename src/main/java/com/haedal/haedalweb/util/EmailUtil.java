package com.haedal.haedalweb.util;

import java.security.SecureRandom;

import com.haedal.haedalweb.constants.EmailConstants;

public class EmailUtil {
	private static final SecureRandom secureRandom = new SecureRandom();

	public static String generateVerificationCode() {
		StringBuilder sb = new StringBuilder(EmailConstants.CODE_LENGTH);
		for (int i = 0; i < EmailConstants.CODE_LENGTH; i++) {
			sb.append(
				EmailConstants.CODE_CHARACTERS.charAt(secureRandom.nextInt(EmailConstants.CODE_CHARACTERS.length())));
		}
		return sb.toString();
	}
}
