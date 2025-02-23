package com.haedal.haedalweb.application.user.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haedal.haedalweb.application.user.dto.FindUserIdResponseDto;
import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import com.haedal.haedalweb.application.user.mapper.UserMapper;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.domain.user.service.UserService;
import com.haedal.haedalweb.security.service.SecurityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAppServiceImpl implements UserAppService {
	private final UserService userService;
	private final SecurityService securityService;

	@Override
	@Transactional(readOnly = true)
	public UserResponseDto getUser(String userId) {
		User user = userService.getUser(userId);

		return UserMapper.toDto(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserResponseDto> getUsers(Sort sort) {
		List<User> users = userService.getUsersByUserStatus(UserStatus.ACTIVE, sort);

		return UserMapper.toDtos(users);
	}

	@Override
	@Transactional
	public void cancelUserAccount() {
		User loggedInUser = securityService.getLoggedInUser();
		userService.cancelUserAccount(loggedInUser);
	}

	@Override
	@Transactional(readOnly = true)
	public FindUserIdResponseDto getUserId(Integer studentNumber, String name) {
		User user = userService.getUserId(studentNumber, name);

		return FindUserIdResponseDto.builder()
			.userId(user.getId())
			.build();
	}
}
