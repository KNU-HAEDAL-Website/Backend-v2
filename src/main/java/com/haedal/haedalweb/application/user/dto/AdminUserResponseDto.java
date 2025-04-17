package com.haedal.haedalweb.application.user.dto;

import java.time.LocalDateTime;

import com.haedal.haedalweb.domain.user.model.JoinSemester;
import com.haedal.haedalweb.domain.user.model.Role;

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
public class AdminUserResponseDto {
	@Schema(description = "유저 아이디", example = "haedal12")
	private String userId;

	@Schema(description = "유저 학번", example = "2024111234")
	private Integer studentNumber;

	@Schema(description = "유저 이름", example = "조대성")
	private String userName;

	@Schema(description = "유저 권한", example = "(ROLE_WEB_MASTER, ROLE_ADMIN, ROLE_TEAM_LEADER, ROLE_MEMBER)")
	private Role role;

	@Schema(description = "가입 날짜")
	private LocalDateTime regDate;

	@Schema(description = "가입학기", example = "2024-1")
	private JoinSemester joinSemester;
}

