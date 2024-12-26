package com.haedal.haedalweb.domain.user.repository;

import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByStudentNumber(Integer studentNumber);
    List<User> findByUserStatus(UserStatus userStatus, Sort sort);
    boolean existsByEmail(String email);
}
