package com.haedal.haedalweb.infrastructure.semester.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.haedal.haedalweb.domain.semester.model.Semester;

public interface SemesterJpaRepository extends JpaRepository<Semester, Long> {
	boolean existsByName(String name);
	Optional<Semester> findByName(String name);
	List<Semester> findAllByNameGreaterThanEqualOrderByNameAsc(String name);
}
