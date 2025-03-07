package com.haedal.haedalweb.application.activity.dto;

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
public class ActivityResponseDto {
	@Schema(description = "활동 id")
	private Long activityId;

	@Schema(description = "활동명")
	private String activityName;

	@Schema(description = "학기 id")
	private Long semesterId;
}
