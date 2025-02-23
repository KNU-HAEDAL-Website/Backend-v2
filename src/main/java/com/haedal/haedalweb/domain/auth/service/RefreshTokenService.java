package com.haedal.haedalweb.domain.auth.service;

public interface RefreshTokenService {

	void registerRefreshToken(String refreshToken, String userId);

	void validateRefreshToken(String refreshToken);

	void removeRefreshToken(String refreshToken);
}
