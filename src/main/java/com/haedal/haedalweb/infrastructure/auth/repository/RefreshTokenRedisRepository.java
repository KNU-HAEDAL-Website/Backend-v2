package com.haedal.haedalweb.infrastructure.auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.haedal.haedalweb.domain.auth.model.RefreshToken;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
