package com.haedal.haedalweb.application.profile.service;

import com.haedal.haedalweb.application.profile.dto.ProfileResponseDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileAppService {
    void updateProfileImage(String userId, MultipartFile profileImageFile);
    ProfileResponseDto getProfile(String userId);
}
