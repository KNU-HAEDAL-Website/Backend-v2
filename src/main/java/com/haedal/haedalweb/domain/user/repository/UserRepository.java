package com.haedal.haedalweb.domain.user.repository;

import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByStudentNumber(Integer studentNumber);
    List<User> findByUserStatus(UserStatus userStatus, Sort sort);
    Optional<User> findByEmail(String email);
}
