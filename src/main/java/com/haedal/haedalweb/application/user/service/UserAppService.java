package com.haedal.haedalweb.application.user.service;

import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserAppService {
    UserResponseDto getUser(String userId);
    List<UserResponseDto> getUsers(Sort sort);
}
