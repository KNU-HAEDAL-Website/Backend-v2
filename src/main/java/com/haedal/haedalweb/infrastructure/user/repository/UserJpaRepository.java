package com.haedal.haedalweb.infrastructure.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;

public interface UserJpaRepository extends JpaRepository<User, String> {
	boolean existsByStudentNumber(Integer studentNumber);

	List<User> findByUserStatus(UserStatus userStatus, Sort sort);

	List<User> findByUserSemesters_Semester_Name(String semesterName, Sort sort);

	boolean existsByEmail(String email);

	Optional<User> findByStudentNumberAndName(Integer studentNumber, String name);
}
