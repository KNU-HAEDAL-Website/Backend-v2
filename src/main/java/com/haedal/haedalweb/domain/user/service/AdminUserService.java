package com.haedal.haedalweb.domain.user.service;

import java.util.List;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import org.springframework.data.domain.Sort;


public interface AdminUserService {
    List<UserResponseDto> getUsers(UserStatus userStatus, Sort sort);

    void updateUserStatus(String userId, UserStatus userStatus);

    void deleteUser(String userId);

    void updateUserRole(String userId, Role role);
}
