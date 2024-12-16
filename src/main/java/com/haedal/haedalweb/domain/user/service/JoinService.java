package com.haedal.haedalweb.domain.user.service;

import com.haedal.haedalweb.application.user.dto.JoinRequestDto;


public interface JoinService {

    void createUserAccount(JoinRequestDto joinRequestDTO);

    void createAdminAccount(JoinRequestDto joinRequestDTO);

    boolean isUserIdDuplicate(String userId);

    boolean isStudentNumberDuplicate(Integer studentNumber);
}
