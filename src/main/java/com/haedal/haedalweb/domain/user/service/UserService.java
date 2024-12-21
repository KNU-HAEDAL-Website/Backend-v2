package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import com.haedal.haedalweb.application.user.dto.UserSummaryResponseDto;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {

    List<UserSummaryResponseDto> getUsers();

    List<User> getUsersByUserStatus(UserStatus userStatus, Sort sort);

    UserResponseDto getMe();

    User getUser(String userId);

    List<User> getUsersByIds(List<String> userIds);

    User getLoggedInUser();

    UserResponseDto convertToUserDTO(User user);

    void validateActiveUsers(List<User> users, List<String> userIds);
}
