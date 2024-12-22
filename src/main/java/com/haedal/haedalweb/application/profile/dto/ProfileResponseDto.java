package com.haedal.haedalweb.application.profile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileResponseDto {
    @Schema(description = "유저 아이디", example = "haedal12")
    private String userId;

    @Schema(description = "유저 이름", example = "조대성")
    private String userName;

    @Schema(description = "유저 학번 (본인 전용)", example = "2024111234")
    private Integer studentNumber;

    @Schema(description = "유저 이메일 (본인 전용)")
    private String email;

    @Schema(description = "유저 권한", example = "(해구르르, 팀장, 일반)")
    private String role;

    @Schema(description = "프로필 이미지 파일 Url")
    private String profileImageUrl;

    @Schema(description = "자기소개")
    private String userIntro;

    @Schema(description = "깃허브 계정 id")
    private String githubAccount;

    @Schema(description = "인스타그램 계정 id")
    private String instaAccount;

}
