package com.haedal.haedalweb.service;

import com.haedal.haedalweb.domain.RefreshToken;
import com.haedal.haedalweb.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String token, String userId) {
        refreshTokenRepository.save(new RefreshToken(token, userId));
    }

    public boolean existsByRefreshToken(String token) {
        return refreshTokenRepository.existsById(token);
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteById(token);
    }
}
