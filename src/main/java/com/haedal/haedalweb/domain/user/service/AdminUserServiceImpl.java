package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.association.model.UserSemester;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.domain.semester.repository.SemesterRepository;
import java.util.List;
import org.springframework.stereotype.Service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.domain.user.repository.UserRepository;
import com.haedal.haedalweb.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl implements AdminUserService {
	private final UserRepository userRepository;
	private final SemesterRepository semesterRepository;

	@Override
	public void approveUser(String userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_ID));

		if (user.getUserStatus() != UserStatus.INACTIVE) {
			throw new BusinessException(ErrorCode.NOT_FOUND_USER_ID);
		}


		List<Semester> semesterList = semesterRepository.findSemestersFrom(user.getJoinedSemester());
		if (semesterList.isEmpty()) {
			throw new IllegalArgumentException("No semesters found starting from: " + user.getJoinedSemester());
		}

		for (Semester semester : semesterList) {
			UserSemester userSemester = UserSemester.builder()
					.user(user)
					.semester(semester)
					.build();
			user.getUserSemesters().add(userSemester);
			semester.getUserSemesters().add(userSemester);
		}

		user.setUserStatus(UserStatus.ACTIVE);
	}

	@Override
	public void expelUser(String userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_ID));

		if (user.getUserStatus() != UserStatus.ACTIVE) {
			throw new BusinessException(ErrorCode.NOT_FOUND_USER_ID);
		}

		user.setUserStatus(UserStatus.DELETED);
		user.setEmail(null);
		user.setStudentNumber(null);
	}

	@Override
	public void removeUser(String userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_ID));

		if (user.getUserStatus() != UserStatus.INACTIVE) {
			throw new BusinessException(ErrorCode.NOT_FOUND_USER_ID);
		}

		userRepository.delete(user);
	}

	@Override
	public void updateUserRole(String userId, Role role) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_ID));

		if (user.getUserStatus() != UserStatus.ACTIVE) {
			throw new BusinessException(ErrorCode.NOT_FOUND_USER_ID);
		}

		user.setRole(role);
	}
}
