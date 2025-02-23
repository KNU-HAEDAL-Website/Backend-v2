package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.user.model.Role;

public interface AdminUserService {

	void approveUser(String userId);

	void expelUser(String userId);

	void removeUser(String userId);

	void updateUserRole(String userId, Role role);
}
