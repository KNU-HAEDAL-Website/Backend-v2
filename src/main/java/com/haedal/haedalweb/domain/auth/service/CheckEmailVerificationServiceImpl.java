package com.haedal.haedalweb.domain.auth.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.auth.model.CheckEmailVerification;
import com.haedal.haedalweb.domain.auth.repository.CheckEmailVerificationRepository;
import com.haedal.haedalweb.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckEmailVerificationServiceImpl implements CheckEmailVerificationService {
    private final CheckEmailVerificationRepository checkEmailVerificationRepository;


    @Override
    public void saveCheckEmailVerification(CheckEmailVerification checkEmailVerification) {
        checkEmailVerificationRepository.save(checkEmailVerification);
    }

    @Override
    public CheckEmailVerification findCheckEmailVerification(String id) {
        return checkEmailVerificationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_ID));
    }
}
