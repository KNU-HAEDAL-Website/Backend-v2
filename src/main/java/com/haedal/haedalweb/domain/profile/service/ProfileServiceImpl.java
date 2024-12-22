package com.haedal.haedalweb.domain.profile.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.profile.model.Profile;
import com.haedal.haedalweb.domain.profile.model.ProfileImage;
import com.haedal.haedalweb.domain.profile.repository.ProfileRepository;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    @Override
    public void generateProfile(User user) {
        Profile profile = Profile.builder()
                .user(user)
                .build();

        ProfileImage profileImage = ProfileImage.builder()
                .profile(profile)
                .build();

        profile.setProfileImage(profileImage);
        profileRepository.save(profile);
    }

    @Override
    public Profile getProfileWithImageAndUser(String userId) {
        Profile profile = profileRepository.findProfileByImageAndUser(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_ID));

        if (profile.getUser().getUserStatus() == UserStatus.DELETED || profile.getUser().getUserStatus() == UserStatus.INACTIVE) {
            throw new BusinessException(ErrorCode.NOT_FOUND_USER_ID);
        }

        return profile;
    }

    @Override
    public void validateAuthorityOfProfileManagement(String userId, User loggedInUser) {
        if (!userId.equals(loggedInUser.getId())) {
            throw new BusinessException(ErrorCode.FORBIDDEN_UPDATE);
        }
    }
}
