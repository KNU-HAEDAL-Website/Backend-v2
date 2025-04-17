package com.haedal.haedalweb.infrastructure.profile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.profile.model.Profile;
import com.haedal.haedalweb.domain.profile.repository.ProfileRepository;
import com.haedal.haedalweb.domain.user.model.JoinSemester;
import com.haedal.haedalweb.domain.user.model.Role;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryAdapter implements ProfileRepository {
	private final ProfileJpaRepository profileJpaRepository;

	@Override
	public Profile save(Profile profile) {
		return profileJpaRepository.save(profile);
	}

	@Override
	public Optional<Profile> findProfileWithUser(String userId) {
		return profileJpaRepository.findProfileWithUser(userId);
	}

	@Override
	public Page<Profile> findProfilePageByRoles(List<Role> roles, Pageable pageable) {
		return profileJpaRepository.findProfilePageByRoles(roles, pageable);
	}

	@Override
	public Page<Profile> findProfilePageByRolesAndJoinSemester(List<Role> roles, JoinSemester joinSemester, Pageable pageable) {
		return profileJpaRepository.findProfilePageByRolesAndJoinSemester(roles, joinSemester, pageable);
	}
}
