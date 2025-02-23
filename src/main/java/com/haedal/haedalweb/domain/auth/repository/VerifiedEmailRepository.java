package com.haedal.haedalweb.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.haedal.haedalweb.domain.auth.model.VerifiedEmail;

@Repository
public interface VerifiedEmailRepository extends CrudRepository<VerifiedEmail, String> {
}
