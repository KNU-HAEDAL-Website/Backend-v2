package com.haedal.haedalweb.domain.auth.service;

public interface RefreshTokenService {

    void saveRefreshToken(String token, String userId);

    void deleteRefreshToken(String token);
}
