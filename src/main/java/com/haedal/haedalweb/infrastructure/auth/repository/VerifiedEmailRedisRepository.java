package com.haedal.haedalweb.infrastructure.auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.haedal.haedalweb.domain.auth.model.VerifiedEmail;

public interface VerifiedEmailRedisRepository extends CrudRepository<VerifiedEmail, String> {
}
