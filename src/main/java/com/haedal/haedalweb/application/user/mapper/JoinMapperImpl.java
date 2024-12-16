package com.haedal.haedalweb.application.user.mapper;

import com.haedal.haedalweb.application.user.dto.JoinRequestDto;
import com.haedal.haedalweb.domain.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class JoinMapperImpl implements JoinMapper {
    @Override
    public User toEntity(JoinRequestDto joinRequestDto) {
        return User.builder()
                .id(joinRequestDto.getUserId())
                .email(joinRequestDto.getEmail())
                .password(joinRequestDto.getPassword())
                .name(joinRequestDto.getUserName())
                .studentNumber(joinRequestDto.getStudentNumber())
                .build();
    }
}
