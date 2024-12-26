package com.haedal.haedalweb.application.user.dto;

import com.haedal.haedalweb.domain.user.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateRoleRequestDto {
    @Schema(description = "유저 권한", example = "(ROLE_ADMIN, ROLE_TEAM_LEADER, ROLE_MEMBER)")
    @NotNull(message = "권한을 필수 입력 항목 입니다.")
    private Role role;
}
