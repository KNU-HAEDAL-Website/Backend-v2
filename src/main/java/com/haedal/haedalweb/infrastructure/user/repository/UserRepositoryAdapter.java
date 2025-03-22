package com.haedal.haedalweb.infrastructure.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public User save(User user) {
		return userJpaRepository.save(user);
	}

	@Override
	public void delete(User user) {
		userJpaRepository.delete(user);
	}

	@Override
	public Optional<User> findById(String userId) {
		return userJpaRepository.findById(userId);
	}

	@Override
	public boolean existsByStudentNumber(Integer studentNumber) {
		return userJpaRepository.existsByStudentNumber(studentNumber);
	}

	@Override
	public List<User> findByUserStatus(UserStatus userStatus, Sort sort) {
		return userJpaRepository.findByUserStatus(userStatus, sort);
	}

	@Override
	public List<User> findByUserStatusAndSemester(UserStatus userStatus, Long semesterId, Sort sort) {
		return userJpaRepository.findByUserStatusAndUserSemesters_Semester_Id(userStatus, semesterId, sort);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userJpaRepository.existsByEmail(email);
	}

	@Override
	public boolean existsById(String userId) {
		return userJpaRepository.existsById(userId);
	}

	@Override
	public Optional<User> findByStudentNumberAndName(Integer studentNumber, String name) {
		return userJpaRepository.findByStudentNumberAndName(studentNumber, name);
	}

	@Override
	public List<User> findAllById(List<String> userIds) {
		return userJpaRepository.findAllById(userIds);
	}
}
