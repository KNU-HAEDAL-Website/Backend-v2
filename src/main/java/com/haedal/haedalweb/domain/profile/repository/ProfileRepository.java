package com.haedal.haedalweb.domain.profile.repository;

import com.haedal.haedalweb.domain.profile.model.Profile;
import com.haedal.haedalweb.domain.user.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT p FROM Profile p " +
            "JOIN FETCH p.user " +
            "JOIN FETCH p.profileImage " +
            "WHERE p.user.id = :userId ")
    Optional<Profile> findProfileWithUser(String userId);

    @Query(
            value = "SELECT p FROM Profile p " +
                    "JOIN FETCH p.user " +
                    "JOIN FETCH p.profileImage " +
                    "WHERE p.user.role IN :roles",
            countQuery = "SELECT count(p) FROM Profile p " +
                         "JOIN p.user " +
                         "WHERE p.user.role IN :roles"
    )
    Page<Profile> findProfilePageByRoles(@Param("roles") List<Role> roles, Pageable pageable);
}
