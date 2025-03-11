package com.haedal.haedalweb.application.profile.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.haedal.haedalweb.application.profile.dto.ProfileRequestDto;
import com.haedal.haedalweb.application.profile.dto.ProfileResponseDto;
import com.haedal.haedalweb.application.profile.mapper.ProfileMapper;
import com.haedal.haedalweb.domain.profile.model.Profile;
import com.haedal.haedalweb.domain.profile.model.ProfileImage;
import com.haedal.haedalweb.domain.profile.service.ProfileService;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.infrastructure.image.ImageRemoveEvent;
import com.haedal.haedalweb.infrastructure.image.ImageSaveRollbackEvent;
import com.haedal.haedalweb.infrastructure.image.ImageUtil;
import com.haedal.haedalweb.security.service.SecurityService;

@Service
public class ProfileAppServiceImpl implements ProfileAppService {
	private final ProfileService profileService;
	private final SecurityService securityService;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final String uploadPath;
	private final String uploadUrl;
	private final String defaultUrl;

	public ProfileAppServiceImpl(ProfileService profileService, SecurityService securityService,
		ApplicationEventPublisher applicationEventPublisher,
		@Value("${file.path.upload-profile-images}") String uploadPath,
		@Value("${file.url.upload-profile-images}") String uploadUrl,
		@Value("${file.url.upload-default-images}") String defaultUrl) {
		this.profileService = profileService;
		this.securityService = securityService;
		this.applicationEventPublisher = applicationEventPublisher;
		this.uploadPath = uploadPath;
		this.uploadUrl = uploadUrl;
		this.defaultUrl = defaultUrl;
	}

	@Transactional
	@Override
	public void updateProfileImage(String userId, MultipartFile profileImageFile) {
		Profile profile = profileService.getProfileWithUser(userId);
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
		if (removeFile == null || removeFile.isEmpty()) {
			return;
		}

		// 모든작업이 Commit 될 시에 이전 이미지 파일 삭제
		applicationEventPublisher.publishEvent(new ImageRemoveEvent(uploadPath, removeFile));
	}

	@Override
	@Transactional
	public void removeProfileImage(String userId) {
		Profile profile = profileService.getProfileWithUser(userId);
		User loggedInUser = securityService.getLoggedInUser();

		// 프로필 삭제 권한 검증
		profileService.validateAuthorityOfProfileManagement(userId, loggedInUser);

		ProfileImage profileImage = profile.getProfileImage();
		String removeFile = profileImage.getSaveFile();

		// 삭제할 이미지 없다면 early return
		if (removeFile == null || removeFile.isEmpty()) {
			return;
		}

		// 이미지 테이블 수정
		profileImage.setOriginalFile(null);
		profileImage.setSaveFile(null);

		// 모든작업이 Commit 될 시에 이전 이미지 파일 삭제
		applicationEventPublisher.publishEvent(new ImageRemoveEvent(uploadPath, removeFile));
	}

	@Transactional
	@Override
	public void updateProfile(String userId, ProfileRequestDto profileRequestDto) {
		Profile profile = profileService.getProfileWithUser(userId);
		User loggedInUser = securityService.getLoggedInUser();

		// 프로필 수정 권한 검증
		profileService.validateAuthorityOfProfileManagement(userId, loggedInUser);

		profile.setIntro(profileRequestDto.getProfileIntro());
		profile.setGithubAccount(profileRequestDto.getGithubAccount());
		profile.setInstaAccount(profileRequestDto.getInstaAccount());
	}

	@Transactional(readOnly = true)
	@Override
	public ProfileResponseDto getProfile(String userId) {
		Profile profile = profileService.getProfileWithUser(userId);
		String saveFile = profile.getProfileImage().getSaveFile();
		String imageUrl = getProfileImageUrl(defaultUrl, uploadUrl, saveFile);

		// 본인 프로필 여부 확인
		if (securityService.isLoggedIn()) {
			User loggedInUser = securityService.getLoggedInUser();
			boolean isSelf = loggedInUser.getId().equals(userId);

			return ProfileMapper.toDto(isSelf, imageUrl, profile);
		}

		return ProfileMapper.toDto(false, imageUrl, profile);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<ProfileResponseDto> getProfilePage(List<Role> roles, Pageable pageable) {
		Page<Profile> profilePage = profileService.getProfilePage(roles, pageable);

		Page<ProfileResponseDto> profileResponsePage = profilePage.map(profile -> {
			String imageUrl = getProfileImageUrl(defaultUrl, uploadUrl, profile.getProfileImage().getSaveFile());

			return ProfileMapper.toDto(false, imageUrl, profile);
		});

		return profileResponsePage;
	}

	private String getProfileImageUrl(String defaultUrl, String uploadUrl, String saveFile) {
		// 프로필 이미지 등록한 적 없다면, default 이미지 url 반환
		if (saveFile == null || saveFile.isEmpty()) {
			return defaultUrl + "/default-profile.png";
		} else {
			// 이미지 URL을 생성하는 유틸 메서드 활용
			return ImageUtil.generateImageUrl(uploadUrl, saveFile);
		}
	}

	@Transactional
	@Override
	public void expelUserAccount(String userId) {
		Profile profile = profileService.getProfileWithUser(userId);
		User loggedInUser = securityService.getLoggedInUser();

		profileService.validateAuthorityOfProfileManagement(userId, loggedInUser);

		profile.getUser().setUserStatus(UserStatus.DELETED);
		profile.getUser().setEmail(null);
		profile.getUser().setStudentNumber(null);
	}
}
