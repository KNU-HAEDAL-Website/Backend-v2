package com.haedal.haedalweb.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailVerificationCodeRequestDto {
    @Schema(description = "이메일", example = "haedal12@gmail.com")
    @Size(max = 63, message = "이메일은 63자 이하여야 합니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private String email;

    @Schema(description = "유저 아이디", example = "haedal12")
    @Size(min = 6, max = 12, message = "ID는 6자 이상 12자 이하여야 합니다.")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "ID는 영어와 숫자만 입력할 수 있습니다.")
    private String userId;

    @Schema(description = "인증 코드", example = "ABcD12")
    @Size(min = 6, max = 6, message = "인증 코드 형식이 맞지 않습니다.")
    @NotBlank(message = "인증 코드는 필수 입력 항목입니다.")
    private String code;
}
