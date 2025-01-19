package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {

    List<User> getUsersByUserStatus(UserStatus userStatus, Sort sort);

    User getUser(String userId);

    List<User> getUsersByIds(List<String> userIds);

    void validateActiveUsers(List<String> userIds, List<User> users);

    void cancelUserAccount(User loggedInUser);

    User getUserId(String email, Integer studentNumber);
}
