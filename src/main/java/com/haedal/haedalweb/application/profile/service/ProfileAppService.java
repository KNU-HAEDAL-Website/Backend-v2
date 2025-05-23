package com.haedal.haedalweb.application.profile.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.haedal.haedalweb.application.profile.dto.ProfileRequestDto;
import com.haedal.haedalweb.application.profile.dto.ProfileResponseDto;
import com.haedal.haedalweb.domain.user.model.JoinSemester;
import com.haedal.haedalweb.domain.user.model.Role;

public interface ProfileAppService {
	void updateProfileImage(String userId, MultipartFile profileImageFile);

	void removeProfileImage(String userId);

	void updateProfile(String userId, ProfileRequestDto profileRequestDto);

	ProfileResponseDto getProfile(String userId);

	Page<ProfileResponseDto> getProfilePage(List<Role> roles, JoinSemester joinSemester, Pageable pageable);

	void expelUserAccount(String userId);
}
