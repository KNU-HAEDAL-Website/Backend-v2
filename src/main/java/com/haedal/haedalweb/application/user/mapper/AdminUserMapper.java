package com.haedal.haedalweb.application.user.mapper;

import java.util.List;

import com.haedal.haedalweb.application.user.dto.AdminUserResponseDto;
import com.haedal.haedalweb.domain.user.model.User;

public class AdminUserMapper {
	private AdminUserMapper() {
	}

	public static AdminUserResponseDto toDto(User user) {
		return AdminUserResponseDto.builder()
			.userId(user.getId())
			.studentNumber(user.getStudentNumber())
			.userName(user.getName())
			.role(user.getRole())
			.regDate(user.getRegDate())
			.joinSemester(user.getJoinSemester())
			.build();
	}

	public static List<AdminUserResponseDto> toDtos(List<User> users) {
		return users.stream()
			.map(AdminUserMapper::toDto)
			.toList();
	}
}
