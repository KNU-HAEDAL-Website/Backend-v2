package com.haedal.haedalweb.application.user.mapper;

import java.util.List;

import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import com.haedal.haedalweb.domain.user.model.User;

public class UserMapper {
	private UserMapper() {
	}

	public static UserResponseDto toDto(User user) {
		return UserResponseDto.builder()
			.userId(user.getId())
			.role(user.getRole())
			.studentNumber(user.getStudentNumber())
			.userName(user.getName())
			.build();
	}

	public static List<UserResponseDto> toDtos(List<User> users) {
		return users.stream()
			.map(UserMapper::toDto)
			.toList();
	}
}
