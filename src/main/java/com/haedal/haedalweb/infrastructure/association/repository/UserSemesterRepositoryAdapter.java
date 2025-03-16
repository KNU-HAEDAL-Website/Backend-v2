package com.haedal.haedalweb.infrastructure.association.repository;

import com.haedal.haedalweb.domain.association.repository.UserSemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserSemesterRepositoryAdapter implements UserSemesterRepository {

    private final UserSemesterJpaRepository userSemesterJpaRepository;
}
