package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.association.model.UserSemester;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.domain.semester.repository.SemesterRepository;
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

		Semester joinedSemester = semesterRepository.findById(user.getJoinedSemesterId())
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_SEMESTER_ID));

		// joinedSemester와 user의 연관관계 추가
		UserSemester userSemester = UserSemester.builder()
				.user(user)
				.semester(joinedSemester)
				.build();
		user.getUserSemesters().add(userSemester);
		joinedSemester.getUserSemesters().add(userSemester);

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
