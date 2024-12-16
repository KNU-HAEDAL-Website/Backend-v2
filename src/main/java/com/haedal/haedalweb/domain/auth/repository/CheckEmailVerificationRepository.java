package com.haedal.haedalweb.domain.auth.repository;

import com.haedal.haedalweb.domain.auth.model.CheckEmailVerification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckEmailVerificationRepository extends CrudRepository<CheckEmailVerification, String> {
}
