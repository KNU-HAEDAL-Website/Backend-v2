package com.haedal.haedalweb.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.haedal.haedalweb.constants.EmailConstants;

public class EmailUtil {
	private static final SecureRandom secureRandom = new SecureRandom();
	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String DIGITS = "0123456789";
	private static final String SPECIALS = "!@#$%^&*()";
	private static final String ALL_CHARS = LETTERS + DIGITS + SPECIALS;
	public static final int PASSWORD_LENGTH = 10;

	public static String generateVerificationCode() {
		StringBuilder sb = new StringBuilder(EmailConstants.CODE_LENGTH);
		for (int i = 0; i < EmailConstants.CODE_LENGTH; i++) {
			sb.append(
				EmailConstants.CODE_CHARACTERS.charAt(secureRandom.nextInt(EmailConstants.CODE_CHARACTERS.length())));
		}
		return sb.toString();
	}

	public static String generateRandomPassword() {
		List<Character> passwordChars = new ArrayList<>();

		// 필수 조건: 각 카테고리에서 반드시 하나씩 선택
		passwordChars.add(LETTERS.charAt(secureRandom.nextInt(LETTERS.length())));
		passwordChars.add(DIGITS.charAt(secureRandom.nextInt(DIGITS.length())));
		passwordChars.add(SPECIALS.charAt(secureRandom.nextInt(SPECIALS.length())));

		// 남은 자리는 ALL_CHARS에서 랜덤하게 채웁니다.
		for (int i = 3; i < PASSWORD_LENGTH; i++) {
			passwordChars.add(ALL_CHARS.charAt(secureRandom.nextInt(ALL_CHARS.length())));
		}

		// 생성된 리스트의 순서를 섞어줍니다.
		Collections.shuffle(passwordChars, secureRandom);

		StringBuilder password = new StringBuilder();
		for (char c : passwordChars) {
			password.append(c);
		}

		return password.toString();
	}
}
