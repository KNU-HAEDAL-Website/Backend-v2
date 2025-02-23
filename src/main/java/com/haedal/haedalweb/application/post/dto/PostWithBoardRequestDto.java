package com.haedal.haedalweb.application.post.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostWithBoardRequestDto extends BasePostRequestDto {
	@Schema(description = "활동 시작일", example = "2024-07-24")
	@NotNull(message = "활동 시작일은 필수입니다.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate postActivityStartDate;

	@Schema(description = "활동 종료일 (생략 가능)", example = "2024-07-24")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate postActivityEndDate;
}
