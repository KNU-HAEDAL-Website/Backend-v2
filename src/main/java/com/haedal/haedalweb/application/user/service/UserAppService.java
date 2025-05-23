package com.haedal.haedalweb.application.user.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.haedal.haedalweb.application.user.dto.FindUserIdResponseDto;
import com.haedal.haedalweb.application.user.dto.ResetPasswordEmailCodeRequestDto;
import com.haedal.haedalweb.application.user.dto.ResetPasswordRequestDto;
import com.haedal.haedalweb.application.user.dto.UpdatePasswordRequestDto;
import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import com.haedal.haedalweb.domain.user.model.JoinSemester;

public interface UserAppService {
	UserResponseDto getUser(String userId);

	List<UserResponseDto> getUsers(Sort sort);

	void cancelUserAccount();

	FindUserIdResponseDto getUserId(Integer studentNumber, String name);

	void updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto);

	void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto);

	void verifyResetPasswordCode(ResetPasswordEmailCodeRequestDto resetPasswordEmailCodeRequestDto);

	List<JoinSemester> getAvailableJoinSemesters();
}
