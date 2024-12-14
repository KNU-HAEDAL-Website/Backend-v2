package com.haedal.haedalweb.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthApplicationServiceImpl implements AuthApplicationService {
    private final TokenService tokenService;

    public void reissueToken(HttpServletRequest request, HttpServletResponse response) {
        tokenService.reissueToken(request, response);
    }
}