package com.haedal.haedalweb.infrastructure.post.repository;

import org.springframework.data.repository.CrudRepository;

import com.haedal.haedalweb.domain.post.model.ViewRecord;

public interface ViewRecordRedisRepository extends CrudRepository<ViewRecord, String> {
}
