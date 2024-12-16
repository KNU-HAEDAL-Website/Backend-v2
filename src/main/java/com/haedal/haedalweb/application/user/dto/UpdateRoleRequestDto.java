package com.haedal.haedalweb.application.user.dto;

import com.haedal.haedalweb.domain.user.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateRoleRequestDto {
    @Schema(description = "유저 권한", example = "(해구르르, 팀장, 일반)")
    private String role;

    public Role getRole() {
        return Role.of(this.role);
    }
}
