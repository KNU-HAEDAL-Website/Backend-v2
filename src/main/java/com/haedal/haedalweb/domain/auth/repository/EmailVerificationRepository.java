package com.haedal.haedalweb.domain.auth.repository;

import com.haedal.haedalweb.domain.auth.model.EmailVerification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationRepository extends CrudRepository<EmailVerification, String> {
}
