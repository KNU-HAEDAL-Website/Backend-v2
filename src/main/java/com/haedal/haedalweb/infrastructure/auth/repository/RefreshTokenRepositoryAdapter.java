package com.haedal.haedalweb.infrastructure.auth.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.auth.model.RefreshToken;
import com.haedal.haedalweb.domain.auth.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

	private final RefreshTokenRedisRepository refreshTokenRedisRepository;

	@Override
	public RefreshToken save(RefreshToken refreshToken) {
		return refreshTokenRedisRepository.save(refreshToken);
	}

	@Override
	public void deleteById(String refreshToken) {
		try {
			refreshTokenRedisRepository.deleteById(refreshToken);
		} catch (EmptyResultDataAccessException e) {
			log.warn("Refresh Token not found: {}", refreshToken);
		}
	}

	@Override
	public boolean existsById(String refreshToken) {
		return refreshTokenRedisRepository.existsById(refreshToken);
	}
}