package com.haedal.haedalweb.domain.auth.repository;

import com.haedal.haedalweb.domain.auth.model.RefreshToken;

public interface RefreshTokenRepository {
	RefreshToken save(RefreshToken refreshToken);

	void deleteById(String refreshToken);

	boolean existsById(String refreshToken);
}
