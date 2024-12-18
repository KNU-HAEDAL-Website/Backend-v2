package com.haedal.haedalweb.domain.auth.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.auth.model.RefreshToken;
import com.haedal.haedalweb.domain.auth.repository.RefreshTokenRepository;
import com.haedal.haedalweb.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void registerRefreshToken(String refreshToken, String userId) {
        refreshTokenRepository.save(new RefreshToken(refreshToken, userId));
    }

    @Override
    public void removeRefreshToken(String refreshToken) {
        try {
            refreshTokenRepository.deleteById(refreshToken);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Refresh Token not found: {}", refreshToken);
        }
    }

    @Override
    public void validateRefreshToken(String refreshToken) {
        if (!refreshTokenRepository.existsById(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }
}
