package com.haedal.haedalweb.domain.semester.repository;

import java.util.List;
import java.util.Optional;

import com.haedal.haedalweb.domain.semester.model.Semester;

public interface SemesterRepository {
	Semester save(Semester semester);

	void delete(Semester semester);

	Optional<Semester> findById(Long semesterId);

	List<Semester> findAllSortedByName();

	boolean existsByName(String name);
}
