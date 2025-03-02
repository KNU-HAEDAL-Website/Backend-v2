package com.haedal.haedalweb.infrastructure.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haedal.haedalweb.domain.post.model.ViewRecord;

public interface ViewRecordJpaRepository extends JpaRepository<ViewRecord, String> {
}
