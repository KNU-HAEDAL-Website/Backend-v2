package com.haedal.haedalweb.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	boolean existsByStudentNumber(Integer studentNumber);

	List<User> findByUserStatus(UserStatus userStatus, Sort sort);

	boolean existsByEmail(String email);

	Optional<User> findByStudentNumberAndName(Integer studentNumber, String name);
}
