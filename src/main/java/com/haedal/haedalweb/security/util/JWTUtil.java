package com.haedal.haedalweb.security.util;

import java.util.Date;
import java.util.HexFormat;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.LoginConstants;
import com.haedal.haedalweb.exception.BusinessException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {
	private final SecretKey secretKey;

	public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
		byte[] secretBytes = HexFormat.of().parseHex(secret);
		secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
		//        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	public String getUserId(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get(LoginConstants.USER_ID_CLAIM, String.class);
	}

	public String getRole(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get(LoginConstants.ROLE_CLAIM, String.class);
	}

	public String getCategory(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get(LoginConstants.CATEGORY_CLAIM, String.class);
	}

	public void validateRefreshToken(String refreshToken) {
		String category;

		try {
			category = getCategory(refreshToken);
		} catch (ExpiredJwtException e) {
			throw new BusinessException(ErrorCode.EXPIRED_REFRESH_TOKEN);
		}

		if (!LoginConstants.REFRESH_TOKEN.equals(category)) {
			throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
		}
	}

	public void validateAccessToken(String accessToken) {
		String category;

		try {
			category = getCategory(accessToken);
		} catch (ExpiredJwtException e) {
			throw new BusinessException(ErrorCode.EXPIRED_ACCESS_TOKEN);
		}

		if (!category.equals(LoginConstants.ACCESS_TOKEN)) {
			throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN);
		}
	}

	public String createJwt(String category, String userId, String role, Long expiredMs) {
		return Jwts.builder()
			.claim(LoginConstants.CATEGORY_CLAIM, category)
			.claim(LoginConstants.USER_ID_CLAIM, userId)
			.claim(LoginConstants.ROLE_CLAIM, role)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + expiredMs))
			.signWith(secretKey)
			.compact();
	}
}
