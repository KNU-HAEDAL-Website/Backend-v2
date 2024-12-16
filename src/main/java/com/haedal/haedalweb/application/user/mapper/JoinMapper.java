package com.haedal.haedalweb.application.user.mapper;

import com.haedal.haedalweb.application.user.dto.JoinRequestDto;
import com.haedal.haedalweb.domain.user.model.User;

public interface JoinMapper {
    User toEntity(JoinRequestDto dto);
}
