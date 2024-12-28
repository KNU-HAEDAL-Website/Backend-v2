package com.haedal.haedalweb.application.profile.mapper;

import com.haedal.haedalweb.application.profile.dto.ProfileResponseDto;
import com.haedal.haedalweb.domain.profile.model.Profile;
import com.haedal.haedalweb.domain.user.model.User;

public class ProfileMapper {
    private ProfileMapper() {
    }

    // 본인일 경우 email, 학번 추가 반환
    public static ProfileResponseDto toDto(boolean isSelf, String imageUrl, Profile profile) {
        User user = profile.getUser();

        return ProfileResponseDto.builder()
                .userId(user.getId())
                .userName(user.getName())
                .role(user.getRole())
                .profileIntro(profile.getIntro())
                .profileImageUrl(imageUrl)
                .githubAccount(profile.getGithubAccount())
                .instaAccount(profile.getInstaAccount())
                .email(isSelf ? user.getEmail() : null)
                .studentNumber(isSelf ? user.getStudentNumber() : null)
                .build();
    }
}
