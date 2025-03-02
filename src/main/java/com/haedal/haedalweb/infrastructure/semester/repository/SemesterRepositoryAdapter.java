package com.haedal.haedalweb.infrastructure.semester.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.domain.semester.repository.SemesterRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SemesterRepositoryAdapter implements SemesterRepository {

	private final SemesterJpaRepository semesterJpaRepository;

	@Override
	public Semester save(Semester semester) {
		return semesterJpaRepository.save(semester);
	}

	@Override
	public void delete(Semester semester) {
		semesterJpaRepository.delete(semester);
	}

	@Override
	public Optional<Semester> findById(Long semesterId) {
		return semesterJpaRepository.findById(semesterId);
	}

	@Override
	public List<Semester> findAllSortedByName() {
		return semesterJpaRepository.findAll(Sort.by("name"));
	}

	@Override
	public boolean existsByName(String name) {
		return semesterJpaRepository.existsByName(name);
	}
}
