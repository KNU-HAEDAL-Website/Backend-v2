package com.haedal.haedalweb.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.haedal.haedalweb.constants.LoginConstants;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.security.dto.CustomUserDetails;
import com.haedal.haedalweb.security.dto.UserDetailsDto;
import com.haedal.haedalweb.security.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String authorization = request.getHeader(LoginConstants.ACCESS_TOKEN);

		if (authorization == null || !authorization.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = authorization.split(" ")[1];
		;
		jwtUtil.validateAccessToken(accessToken);

		String userId = jwtUtil.getUserId(accessToken);
		String role = jwtUtil.getRole(accessToken);
		UserDetailsDto userDetailsDTO = UserDetailsDto.builder()
			.id(userId)
			.role(Role.valueOf(role))
			.build();

		CustomUserDetails customUserDetails = new CustomUserDetails(userDetailsDTO);
		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
			customUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response);
	}
}
