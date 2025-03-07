package com.haedal.haedalweb.application.semester.dto;

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
public class SemesterResponseDto {
	@Schema(description = "학기 id")
	private Long semesterId;

	@Schema(description = "학기명")
	private String semesterName;
}
