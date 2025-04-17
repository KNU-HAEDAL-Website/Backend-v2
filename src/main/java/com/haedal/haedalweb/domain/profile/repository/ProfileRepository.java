package com.haedal.haedalweb.domain.profile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.haedal.haedalweb.domain.profile.model.Profile;
import com.haedal.haedalweb.domain.user.model.JoinSemester;
import com.haedal.haedalweb.domain.user.model.Role;

public interface ProfileRepository {
	Profile save(Profile profile);

	Optional<Profile> findProfileWithUser(String userId);

	Page<Profile> findProfilePageByRoles(List<Role> roles, Pageable pageable);

	Page<Profile> findProfilePageByRolesAndJoinSemester(List<Role> roles, JoinSemester joinSemester, Pageable pageable);
}
