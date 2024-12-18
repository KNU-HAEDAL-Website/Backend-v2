package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import com.haedal.haedalweb.application.user.dto.UserSummaryResponseDto;

import java.util.List;

public interface UserService {

    List<UserSummaryResponseDto> getUsers();

    UserResponseDto getMe();

    User getUser(String userId);

    List<User> getUsersByIds(List<String> userIds);

    User getLoggedInUser();

    UserResponseDto convertToUserDTO(User user);
}
