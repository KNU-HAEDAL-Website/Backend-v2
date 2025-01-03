package com.haedal.haedalweb.domain.post.repository;

import com.haedal.haedalweb.domain.post.model.ViewRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRecordRepository extends CrudRepository<ViewRecord, String> {
}
