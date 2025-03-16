package com.haedal.haedalweb.infrastructure.association.repository;

import com.haedal.haedalweb.domain.association.model.UserSemester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSemesterJpaRepository extends JpaRepository<UserSemester, String> {
}
