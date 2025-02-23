package com.haedal.haedalweb.security.dto;

import com.haedal.haedalweb.domain.user.model.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserDetailsDto {
	private String id;
	private String password;
	private Role role;
}
