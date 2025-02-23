package com.haedal.haedalweb.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.security.dto.LoginRequestDto;
import com.haedal.haedalweb.security.service.TokenAppService;
import com.haedal.haedalweb.util.ResponseUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private final AuthenticationManager authenticationManager;
	private final TokenAppService tokenAppService;

	public LoginFilter(AuthenticationManager authenticationManager, TokenAppService tokenAppService) {
		super(authenticationManager);
		this.authenticationManager = authenticationManager;
		this.tokenAppService = tokenAppService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		LoginRequestDto loginRequestDTO = parseLoginRequest(request);

		String userId = loginRequestDTO.getUserId();
		String password = loginRequestDTO.getPassword();

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, password, null);

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authentication) {
		String userId = authentication.getName();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String role = auth.getAuthority();

		tokenAppService.issueToken(response, userId, role);
		ResponseUtil.sendSuccessResponse(response, SuccessCode.LOGIN_SUCCESS);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) {
		if (failed instanceof AuthenticationServiceException) {
			throw new BusinessException(ErrorCode.INVALID_LOGIN_CONTENTS_TYPE);
		}

		throw new BusinessException(ErrorCode.FAILED_LOGIN);
	}

	private LoginRequestDto parseLoginRequest(HttpServletRequest request) {
		try {
			ServletInputStream inputStream = request.getInputStream();
			String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

			return objectMapper.readValue(messageBody, LoginRequestDto.class);
		} catch (IOException e) {
			throw new AuthenticationServiceException(ErrorCode.INVALID_LOGIN_CONTENTS_TYPE.getMessage());
		}
	}
}