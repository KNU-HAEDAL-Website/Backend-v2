package com.haedal.haedalweb.application.profile.service;

import com.haedal.haedalweb.application.profile.dto.ProfileRequestDto;
import com.haedal.haedalweb.application.profile.dto.ProfileResponseDto;
import com.haedal.haedalweb.domain.user.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProfileAppService {
    void updateProfileImage(String userId, MultipartFile profileImageFile);

    void updateProfile(String userId, ProfileRequestDto profileRequestDto);

    ProfileResponseDto getProfile(String userId);

    Page<ProfileResponseDto> getProfilePage(List<Role> roles, Pageable pageable);
}
