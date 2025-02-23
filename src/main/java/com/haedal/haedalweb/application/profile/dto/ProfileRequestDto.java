package com.haedal.haedalweb.application.profile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileRequestDto {
	@Schema(description = "프로필 소개", example = "안녕하세요 반갑습니다")
	@Size(max = 127, message = "소개는 100자 이하여야 합니다.")
	private String profileIntro;

	@Schema(description = "깃허브 계정 id")
	@Size(max = 63, message = "깃허브 계정은 30자 이하여야 합니다.")
	private String githubAccount;

	@Schema(description = "인스타그램 계정 id")
	@Size(max = 63, message = "인스타그램 계정은 30자 이하여야 합니다.")
	private String instaAccount;
}
