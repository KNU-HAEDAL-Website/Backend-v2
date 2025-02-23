package com.haedal.haedalweb.security.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.repository.UserRepository;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.security.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SecurityServiceImpl implements SecurityService {
	private final UserRepository userRepository;

	@Override
	public boolean isLoggedIn() {
		// 현재 사용자가 로그인되어 있는지 확인하는 로직
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && authentication.isAuthenticated()
			&& !(authentication instanceof AnonymousAuthenticationToken);
	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
			|| authentication instanceof AnonymousAuthenticationToken) {
			throw new BusinessException(ErrorCode.NOT_AUTHENTICATED_USER);
		}

		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		String userId = userDetails.getUsername();

		return userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_AUTHENTICATED_USER));
	}
}
