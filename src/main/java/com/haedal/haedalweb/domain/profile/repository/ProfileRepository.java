package com.haedal.haedalweb.domain.profile.repository;

import com.haedal.haedalweb.domain.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT p FROM Profile p " +
            "LEFT JOIN FETCH p.user " +
            "LEFT JOIN FETCH p.profileImage " +
            "WHERE p.user.id = :userId ")
    Optional<Profile> findProfileByImageAndUser(String userId);
}
