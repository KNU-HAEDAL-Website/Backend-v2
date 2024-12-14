package com.haedal.haedalweb.domain.auth.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.auth.model.RefreshToken;
import com.haedal.haedalweb.domain.auth.repository.RefreshTokenRepository;
import com.haedal.haedalweb.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String token, String userId) {
        refreshTokenRepository.save(new RefreshToken(token, userId));
    }

    public void deleteRefreshToken(String token) {
        try {
            refreshTokenRepository.deleteById(token);
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }
}
