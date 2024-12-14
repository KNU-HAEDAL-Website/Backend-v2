package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.web.user.dto.UserResponseDto;
import com.haedal.haedalweb.web.user.dto.UserSummaryResponseDto;

import java.util.List;

public interface UserService {

    List<UserSummaryResponseDto> getUsers();

    UserResponseDto getMe();

    User findUserById(String userId);

    List<User> findUserByIds(List<String> userIds);

    User getLoggedInUser();

    UserResponseDto convertToUserDTO(User user);
}
