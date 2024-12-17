package com.haedal.haedalweb.domain.auth.repository;

import com.haedal.haedalweb.domain.auth.model.VerifiedEmail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifiedEmailRepository extends CrudRepository<VerifiedEmail, String> {
}
