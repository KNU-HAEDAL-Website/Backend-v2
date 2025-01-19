package com.haedal.haedalweb.application.user.service;

import com.haedal.haedalweb.application.user.dto.AdminUserResponseDto;
import com.haedal.haedalweb.application.user.dto.UpdateRoleRequestDto;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AdminUserAppService {
    List<AdminUserResponseDto> getUsers(UserStatus userStatus, Sort sort);

    void approveUser(String userId);

    void rejectUser(String userId);

    void expelUser(String userId);

    void updateUserRole(String userId, UpdateRoleRequestDto updateRoleRequestDto);
}
