package com.haedal.haedalweb.security.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.LoginConstants;
import com.haedal.haedalweb.domain.auth.service.RefreshTokenService;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.security.util.JWTUtil;
import com.haedal.haedalweb.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public void issueToken(HttpServletResponse response, String userId, String role) {
        String accessToken = jwtUtil.createJwt(LoginConstants.ACCESS_TOKEN, userId, role, LoginConstants.ACCESS_TOKEN_EXPIRATION_TIME_MS);
        String refreshToken = jwtUtil.createJwt(LoginConstants.REFRESH_TOKEN, userId, role, LoginConstants.REFRESH_TOKEN_EXPIRATION_TIME_MS);

        refreshTokenService.saveRefreshToken(refreshToken, userId);

        response.setHeader(LoginConstants.ACCESS_TOKEN, "Bearer " + accessToken);
        response.addCookie(CookieUtil.createCookie(LoginConstants.REFRESH_TOKEN, refreshToken, (int) LoginConstants.REFRESH_TOKEN_EXPIRATION_TIME_S));
    }

    public void reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getCookieValue(request, LoginConstants.REFRESH_TOKEN)
                .orElseThrow(() -> new BusinessException(ErrorCode.NULL_REFRESH_TOKEN));

        jwtUtil.validateRefreshToken(refreshToken);
        refreshTokenService.deleteRefreshToken(refreshToken);

        String userId = jwtUtil.getUserId(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        issueToken(response, userId, role);
    }
}
