package com.haedal.haedalweb.infrastructure.semester.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haedal.haedalweb.domain.semester.model.Semester;

public interface SemesterJpaRepository extends JpaRepository<Semester, Long> {
	boolean existsByName(String name);
}
