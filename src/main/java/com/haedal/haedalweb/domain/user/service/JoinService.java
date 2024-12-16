package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.domain.user.model.User;


public interface JoinService {

    void createAccount(User user);

    void checkUserIdDuplicate(String userId);

    void checkStudentNumberDuplicate(Integer studentNumber);

    void checkEmailDuplicate(String email);
}
