package com.haedal.haedalweb.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthApplicationService {
    void reissueToken(HttpServletRequest request, HttpServletResponse response);
}
