package com.haedal.haedalweb.application.user.service;

import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import com.haedal.haedalweb.application.user.mapper.UserMapper;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAppServiceImpl implements UserAppService {
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getMe() {
        User user = userService.getLoggedInUser();

        return UserMapper.toDto(user);
    }


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
}
