package com.haedal.haedalweb.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResetPasswordRequestDto {
	@Schema(description = "유저 아이디", example = "haedal12")
	@Size(min = 6, max = 12, message = "ID는 6자 이상 12자 이하여야 합니다.")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "ID는 영어와 숫자만 입력할 수 있습니다.")
	private String userId;

	@Schema(description = "유저 학번", example = "2024111234")
	@Min(1_900_000_000)
	@Max(2_100_000_000)
	private Integer studentNumber;
}
