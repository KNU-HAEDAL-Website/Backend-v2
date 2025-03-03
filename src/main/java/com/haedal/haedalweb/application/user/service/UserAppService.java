package com.haedal.haedalweb.application.user.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.haedal.haedalweb.application.user.dto.FindUserIdResponseDto;
import com.haedal.haedalweb.application.user.dto.UpdatePasswordRequestDto;
import com.haedal.haedalweb.application.user.dto.UserResponseDto;

public interface UserAppService {
	UserResponseDto getUser(String userId);

	List<UserResponseDto> getUsers(Sort sort);

	void cancelUserAccount();

	FindUserIdResponseDto getUserId(Integer studentNumber, String name);

	void updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto);
}
