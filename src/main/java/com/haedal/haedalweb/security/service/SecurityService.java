package com.haedal.haedalweb.security.service;

import com.haedal.haedalweb.domain.user.model.User;

public interface SecurityService {
	boolean isLoggedIn();

	User getLoggedInUser();
}
