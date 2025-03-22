package com.haedal.haedalweb.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;

public interface UserRepository {
	User save(User user);

	void delete(User user);

	Optional<User> findById(String userId);

	boolean existsByStudentNumber(Integer studentNumber);

	List<User> findByUserStatus(UserStatus userStatus, Sort sort);

	List<User> findByUserStatusAndSemester(UserStatus userStatus, Long semesterId, Sort sort);

	boolean existsByEmail(String email);

	boolean existsById(String userId); // 추가

	Optional<User> findByStudentNumberAndName(Integer studentNumber, String name);

	List<User> findAllById(List<String> userIds);
}
