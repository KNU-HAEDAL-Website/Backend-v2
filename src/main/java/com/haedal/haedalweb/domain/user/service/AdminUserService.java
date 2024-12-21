package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.UserStatus;


public interface AdminUserService {
    void updateUserStatus(String userId, UserStatus userStatus);

    void removeUser(String userId);

    void updateUserRole(String userId, Role role);
}
