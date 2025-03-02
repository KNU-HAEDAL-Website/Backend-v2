package com.haedal.haedalweb.infrastructure.post.repository;

import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.post.model.ViewRecord;
import com.haedal.haedalweb.domain.post.repository.ViewRecordRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ViewRecordRepositoryAdapter implements ViewRecordRepository {

	private final ViewRecordJpaRepository viewRecordJpaRepository;

	@Override
	public boolean existsById(String viewRecordId) {
		return viewRecordJpaRepository.existsById(viewRecordId);
	}

	@Override
	public ViewRecord save(ViewRecord viewRecord) {
		return viewRecordJpaRepository.save(viewRecord);
	}
}
