package com.haedal.haedalweb.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TokenService {
    void issueToken(HttpServletResponse response, String userId, String role);
    void reissueToken(HttpServletRequest request, HttpServletResponse response);
}
