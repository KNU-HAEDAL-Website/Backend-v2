package com.haedal.haedalweb.domain.post.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.post.model.ViewRecord;

@Repository
public interface ViewRecordRepository extends CrudRepository<ViewRecord, String> {
}
