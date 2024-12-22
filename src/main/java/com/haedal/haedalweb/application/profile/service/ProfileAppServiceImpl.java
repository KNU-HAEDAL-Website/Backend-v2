package com.haedal.haedalweb.application.profile.service;

import com.haedal.haedalweb.application.profile.dto.ProfileResponseDto;
import com.haedal.haedalweb.application.profile.mapper.ProfileMapper;
import com.haedal.haedalweb.domain.profile.model.Profile;
import com.haedal.haedalweb.domain.profile.model.ProfileImage;
import com.haedal.haedalweb.domain.profile.service.ProfileService;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.service.UserService;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.infrastructure.image.ImageRemoveEvent;
import com.haedal.haedalweb.infrastructure.image.ImageSaveRollbackEvent;
import com.haedal.haedalweb.infrastructure.image.ImageUtil;
import com.haedal.haedalweb.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class ProfileAppServiceImpl implements ProfileAppService {
    private final ProfileService profileService;
    private final UserService userService;
    private final SecurityService securityService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final String uploadPath;
    private final String uploadUrl;
    private final String defaultUrl;

    public ProfileAppServiceImpl(ProfileService profileService, UserService userService, SecurityService securityService, ApplicationEventPublisher applicationEventPublisher, @Value("${file.path.upload-profile-images}") String uploadPath, @Value("${file.url.upload-profile-images}") String uploadUrl, @Value("${file.url.upload-default-images}") String defaultUrl) {
        this.profileService = profileService;
        this.userService = userService;
        this.securityService = securityService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.uploadPath = uploadPath;
        this.uploadUrl = uploadUrl;
        this.defaultUrl = defaultUrl;
    }

    @Transactional
    @Override
    public void updateProfileImage(String userId, MultipartFile profileImageFile) {
        Profile profile = profileService.getProfileWithImageAndUser(userId);
        User loggedInUser = securityService.getLoggedInUser();

        // 프로필 수정 권한 검증
        profileService.validateAuthorityOfProfileManagement(userId, loggedInUser);

        // 새로운 이미지 파일 저장
        String originalFile = profileImageFile.getOriginalFilename();
        String saveFile = UUID.randomUUID() + "." + ImageUtil.getExtension(originalFile);
        ImageUtil.uploadImage(profileImageFile, uploadPath, saveFile);

        // ROLLBACK 발생시 저장한 이미지 파일 삭제
        applicationEventPublisher.publishEvent(new ImageSaveRollbackEvent(uploadPath, saveFile));

        ProfileImage profileImage = profile.getProfileImage();
        String removeFile = profileImage.getSaveFile();

        // 이미지 테이블 수정
        profileImage.setOriginalFile(originalFile);
        profileImage.setSaveFile(saveFile);

        // 삭제할 이미지 없다면 early return
        if (removeFile == null || removeFile.isEmpty()) return;

        // 모든작업이 Commit 될 시에 이전 이미지 파일 삭제
        applicationEventPublisher.publishEvent(new ImageRemoveEvent(uploadPath, removeFile));
    }

    @Transactional(readOnly = true)
    @Override
    public ProfileResponseDto getProfile(String userId) {
        Profile profile = profileService.getProfileWithImageAndUser(userId);
        String saveFile = profile.getProfileImage().getSaveFile();
        String imageUrl;

        // 프로필 이미지 등록한 적 없다면, default 이미지 url 반환
        if (saveFile == null || saveFile.isEmpty()) {
            imageUrl = defaultUrl + "/default-profile.png";
        } else {
            imageUrl = ImageUtil.generateImageUrl(uploadUrl, saveFile);
        }

        // 본인 프로필 여부 확인
        if (userService.isLoggedIn()) {
            User loggedInUser = userService.getLoggedInUser();
            boolean isSelf = loggedInUser.getId().equals(userId);

            return ProfileMapper.toDto(isSelf, imageUrl, profile);
        }

        return ProfileMapper.toDto(false, imageUrl, profile);
    }
}
