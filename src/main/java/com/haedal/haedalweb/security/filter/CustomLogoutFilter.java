package com.haedal.haedalweb.security.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.haedal.haedalweb.constants.LoginConstants;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.security.service.TokenAppService;
import com.haedal.haedalweb.util.CookieUtil;
import com.haedal.haedalweb.util.ResponseUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomLogoutFilter extends GenericFilterBean {
	private final TokenAppService tokenAppService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws
		IOException,
		ServletException {
		doFilter((HttpServletRequest)request, (HttpServletResponse)response, filterChain);
	}

	private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
		IOException,
		ServletException {
		String servletPath = request.getServletPath();
		String requestMethod = request.getMethod();

		if (!servletPath.equals("/logout") || !requestMethod.equals("POST")) {
			filterChain.doFilter(request, response);
			return;
		}

		CookieUtil.getCookieValue(request, LoginConstants.REFRESH_TOKEN)
			.ifPresentOrElse(
				refreshToken -> {
					try {
						tokenAppService.logout(refreshToken);
					} catch (Exception e) {
						log.warn("Failed to validate or delete refresh token: {}", e.getMessage());
					}
				},
				() -> log.info("No Refresh Token found in the request")
			);

		CookieUtil.deleteByKey(response, LoginConstants.REFRESH_TOKEN);
		ResponseUtil.sendSuccessResponse(response, SuccessCode.LOGOUT_SUCCESS);
	}
}