package com.haedal.haedalweb.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatePasswordRequestDto {

	@Schema(description = "현재 비밀번호", example = "abc1234!")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,20}$",
		message = "비밀번호는 영문, 숫자, 특수문자(!@#$%^&*())를 혼용하여 8자 이상 20자 이하로 설정해야 합니다.")
	private String currentPassword;

	@Schema(description = "새 비밀번호", example = "abc1234!")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,20}$",
		message = "비밀번호는 영문, 숫자, 특수문자(!@#$%^&*())를 혼용하여 8자 이상 20자 이하로 설정해야 합니다.")
	private String newPassword;
}
