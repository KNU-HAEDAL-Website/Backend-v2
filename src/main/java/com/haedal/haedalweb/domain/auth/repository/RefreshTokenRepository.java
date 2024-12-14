package com.haedal.haedalweb.domain.auth.repository;

import com.haedal.haedalweb.domain.auth.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
