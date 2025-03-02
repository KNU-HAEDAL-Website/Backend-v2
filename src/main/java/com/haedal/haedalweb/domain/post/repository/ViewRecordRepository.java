package com.haedal.haedalweb.domain.post.repository;

import com.haedal.haedalweb.domain.post.model.ViewRecord;

public interface ViewRecordRepository {
	boolean existsById(String viewRecordId);

	ViewRecord save(ViewRecord viewRecord);
}
