package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.user.model.User;

public interface JoinService {

	void registerAccount(User user);

	void validateUserId(String userId);

	void validateStudentNumber(Integer studentNumber);

	void validateEmail(String email);
}
