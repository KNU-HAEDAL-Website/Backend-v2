package com.haedal.haedalweb.infrastructure.auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.haedal.haedalweb.domain.auth.model.EmailVerification;

public interface EmailVerificationRedisRepository extends CrudRepository<EmailVerification, String> {
}
