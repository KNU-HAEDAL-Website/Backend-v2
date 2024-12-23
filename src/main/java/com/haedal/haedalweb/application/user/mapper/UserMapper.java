package com.haedal.haedalweb.application.user.mapper;


import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import com.haedal.haedalweb.domain.user.model.User;

import java.util.List;

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
