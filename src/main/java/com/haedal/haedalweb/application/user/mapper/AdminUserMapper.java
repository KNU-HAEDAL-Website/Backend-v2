package com.haedal.haedalweb.application.user.mapper;

import com.haedal.haedalweb.application.user.dto.AdminUserResponseDto;
import com.haedal.haedalweb.domain.user.model.User;
import java.util.List;

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
                .build();
    }

    public static List<AdminUserResponseDto> toDtos(List<User> users) {
        return users.stream()
                .map(AdminUserMapper::toDto)
                .toList();
    }
}
