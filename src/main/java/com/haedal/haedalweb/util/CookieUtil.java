package com.haedal.haedalweb.util;

import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CookieUtil {

	private CookieUtil() {
	}

	public static Cookie createCookie(String key, String value, int maxAge) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAge);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setHttpOnly(true);

		return cookie;
	}

	public static Optional<String> getCookieValue(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return Optional.empty();
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(key)) {
				return Optional.of(cookie.getValue());
			}
		}

		return Optional.empty();
	}

	public static void deleteByKey(HttpServletResponse response, String key) {
		if (key == null || key.isEmpty()) {
			log.warn("Invalid cookie key: key is null or empty");
			return;
		}

		Cookie cookie = new Cookie(key, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");

		response.addCookie(cookie);
	}
}
