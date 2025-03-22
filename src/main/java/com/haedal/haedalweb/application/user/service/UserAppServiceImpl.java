package com.haedal.haedalweb.application.user.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haedal.haedalweb.application.user.dto.FindUserIdResponseDto;
import com.haedal.haedalweb.application.user.dto.ResetPasswordEmailCodeRequestDto;
import com.haedal.haedalweb.application.user.dto.ResetPasswordRequestDto;
import com.haedal.haedalweb.application.user.dto.UpdatePasswordRequestDto;
import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import com.haedal.haedalweb.application.user.mapper.UserMapper;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.auth.service.EmailSenderService;
import com.haedal.haedalweb.domain.auth.service.EmailVerificationService;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.domain.user.service.UserService;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.security.service.SecurityService;
import com.haedal.haedalweb.util.EmailUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAppServiceImpl implements UserAppService {
	private final UserService userService;
	private final SecurityService securityService;
	private final PasswordEncoder passwordEncoder;
	private final EmailSenderService emailSenderService;
	private final EmailVerificationService emailVerificationService;

	@Override
	@Transactional(readOnly = true)
	public UserResponseDto getUser(String userId) {
		User user = userService.getUser(userId);

		return UserMapper.toDto(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserResponseDto> getUsers(Long semesterId, Sort sort) {
		List<User> users;
		if (semesterId == null) {
			users = userService.getUsersByUserStatus(UserStatus.ACTIVE, sort);
		}
		else {
			users = userService.getUsersByUserStatusAndSemester(UserStatus.ACTIVE, semesterId, sort);
		}
		return UserMapper.toDtos(users);
	}

	@Override
	@Transactional
	public void cancelUserAccount() {
		User loggedInUser = securityService.getLoggedInUser();
		userService.cancelUserAccount(loggedInUser);
	}

	@Override
	@Transactional(readOnly = true)
	public FindUserIdResponseDto getUserId(Integer studentNumber, String name) {
		User user = userService.getUserId(studentNumber, name);

		return FindUserIdResponseDto.builder()
			.userId(user.getId())
			.build();
	}

	@Override
	@Transactional
	public void updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto) {
		User loggedInUser = securityService.getLoggedInUser();

		if (!passwordEncoder.matches(updatePasswordRequestDto.getCurrentPassword(), loggedInUser.getPassword())) {
			throw new BusinessException(ErrorCode.BAD_REQUEST_PASSWORD);
		}
		loggedInUser.setPassword(passwordEncoder.encode(updatePasswordRequestDto.getNewPassword()));
	}

	@Override
	@Transactional
	public void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto) {
		User user = userService.getUser(resetPasswordRequestDto.getUserId());

		if (!user.getStudentNumber().equals(resetPasswordRequestDto.getStudentNumber())) {
			throw new BusinessException(ErrorCode.NOT_FOUND_USER_ID);
		}

		// 인증 코드 생성 후 저장
		String code = EmailUtil.generateVerificationCode();
		emailVerificationService.registerEmailVerification(user.getEmail(), code);

		// 인증 코드 전송
		emailSenderService.sendVerificationEmail(user.getEmail(), code);
	}

	@Override
	@Transactional
	public void verifyResetPasswordCode(ResetPasswordEmailCodeRequestDto resetPasswordEmailCodeRequestDto) {
		User user = userService.getUser(resetPasswordEmailCodeRequestDto.getUserId());

		// 인증 코드 검증
		emailVerificationService.validateCode(user.getEmail(),
			resetPasswordEmailCodeRequestDto.getCode());

		// 랜덤 비밀번호 전송
		String password = EmailUtil.generateRandomPassword();
		emailSenderService.sendRandomPassword(user.getEmail(), password);

		// 비밀번호 초기화
		user.setPassword(passwordEncoder.encode(password));
	}
}
