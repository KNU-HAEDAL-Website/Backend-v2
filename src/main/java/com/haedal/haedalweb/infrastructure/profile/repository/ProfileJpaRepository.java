package com.haedal.haedalweb.infrastructure.profile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.haedal.haedalweb.domain.profile.model.Profile;
import com.haedal.haedalweb.domain.user.model.JoinSemester;
import com.haedal.haedalweb.domain.user.model.Role;

public interface ProfileJpaRepository extends JpaRepository<Profile, Long> {

	@Query(
		"SELECT p FROM Profile p "
			+ "JOIN FETCH p.user "
			+ "JOIN FETCH p.profileImage "
			+ "WHERE p.user.id = :userId"
	)
	Optional<Profile> findProfileWithUser(@Param("userId") String userId);

	@Query(
		value = "SELECT p FROM Profile p "
			+ "JOIN FETCH p.user "
			+ "JOIN FETCH p.profileImage "
			+ "WHERE p.user.role IN :roles "
			+ "AND p.user.userStatus = 'ACTIVE'",
		countQuery = "SELECT count(p) FROM Profile p "
			+ "JOIN p.user "
			+ "WHERE p.user.role IN :roles "
			+ "AND p.user.userStatus = 'ACTIVE'"
	)
	Page<Profile> findProfilePageByRoles(@Param("roles") List<Role> roles, Pageable pageable);

	@Query(
		value = "SELECT p FROM Profile p "
			+ "JOIN FETCH p.user "
			+ "JOIN FETCH p.profileImage "
			+ "WHERE p.user.role IN :roles "
			+ "AND p.user.joinSemester = :joinSemester "
			+ "AND p.user.userStatus = 'ACTIVE'",
		countQuery = "SELECT count(p) FROM Profile p "
			+ "JOIN p.user "
			+ "WHERE p.user.role IN :roles "
			+ "AND p.user.joinSemester = :joinSemester "
			+ "AND p.user.userStatus = 'ACTIVE'"
	)
	Page<Profile> findProfilePageByRolesAndJoinSemester(
		@Param("roles") List<Role> roles,
		@Param("joinSemester") JoinSemester joinSemester,
		Pageable pageable);
}
