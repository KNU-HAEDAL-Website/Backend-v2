package com.haedal.haedalweb.security.filter;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.LoginConstants;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.domain.auth.service.RefreshTokenService;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.security.util.JWTUtil;
import com.haedal.haedalweb.util.CookieUtil;
import com.haedal.haedalweb.util.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomLogoutFilter extends GenericFilterBean {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String servletPath = request.getServletPath();
        String requestMethod = request.getMethod();

        if (!servletPath.equals("/logout") || !requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = CookieUtil.getCookieValue(request, LoginConstants.REFRESH_TOKEN)
                .orElseThrow(() -> new BusinessException(ErrorCode.NULL_REFRESH_TOKEN));

        jwtUtil.validateRefreshToken(refreshToken);
        refreshTokenService.deleteRefreshToken(refreshToken);
        CookieUtil.deleteByKey(response, LoginConstants.REFRESH_TOKEN);
        ResponseUtil.sendSuccessResponse(response, SuccessCode.LOGOUT_SUCCESS);
    }
}