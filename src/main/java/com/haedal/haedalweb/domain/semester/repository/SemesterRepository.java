package com.haedal.haedalweb.domain.semester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.semester.model.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
	boolean existsByName(String name);
}
