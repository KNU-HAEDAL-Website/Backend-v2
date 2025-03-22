package com.haedal.haedalweb.domain.user.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;

public interface UserService {

	List<User> getUsersByUserStatus(UserStatus userStatus, Sort sort);

	User getUser(String userId);

	List<User> getUsersByIds(List<String> userIds);

	List<User> getUsersByUserStatusAndSemester(UserStatus userStatus, Long semesterId, Sort sort);

	void validateActiveUsers(List<String> userIds, List<User> users);

	void cancelUserAccount(User loggedInUser);

	User getUserId(Integer studentNumber, String name);
}
