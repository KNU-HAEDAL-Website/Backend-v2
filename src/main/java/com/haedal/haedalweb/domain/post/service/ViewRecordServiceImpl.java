package com.haedal.haedalweb.domain.post.service;

import com.haedal.haedalweb.domain.post.model.ViewRecord;
import com.haedal.haedalweb.domain.post.repository.ViewRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewRecordServiceImpl implements ViewRecordService {
    public final ViewRecordRepository viewRecordRepository;

    @Override
    public boolean existsById(String viewRecordId) {
        return viewRecordRepository.existsById(viewRecordId);
    }

    @Override
    public void registerViewRecord(String viewRecordId) {
        ViewRecord viewRecord = new ViewRecord(viewRecordId);
        viewRecordRepository.save(viewRecord);
    }
}