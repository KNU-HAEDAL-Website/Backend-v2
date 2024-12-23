package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {

    List<User> getUsersByUserStatus(UserStatus userStatus, Sort sort);

    User getUser(String userId);

    List<User> getUsersByIds(List<String> userIds);

    void validateActiveUsers(List<User> users, List<String> userIds);
}
