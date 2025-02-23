package com.haedal.haedalweb.domain.post.service;

public interface ViewRecordService {
	boolean existsById(String viewRecordId);

	void registerViewRecord(String viewRecordId);
}
