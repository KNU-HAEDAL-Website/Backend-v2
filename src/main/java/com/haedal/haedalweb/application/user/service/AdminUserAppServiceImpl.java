package com.haedal.haedalweb.application.user.service;

import com.haedal.haedalweb.application.user.dto.AdminUserResponseDto;
import com.haedal.haedalweb.application.user.dto.UpdateRoleRequestDto;
import com.haedal.haedalweb.application.user.mapper.AdminUserMapper;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.domain.user.service.AdminUserService;
import com.haedal.haedalweb.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserAppServiceImpl implements AdminUserAppService {
    private final AdminUserService adminUserService;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public List<AdminUserResponseDto> getUsers(UserStatus userStatus, Sort sort) {
        List<User> users = userService.getUsersByUserStatus(userStatus, sort);

        return AdminUserMapper.toDtos(users);
    }

    @Override
    @Transactional
    public void approveUser(String userId) {
        adminUserService.updateUserStatus(userId, UserStatus.ACTIVE);
    }

    @Override
    @Transactional
    public void rejectUser(String userId) {
        adminUserService.updateUserStatus(userId, UserStatus.DELETED);
    }

    @Override
    @Transactional
    public void removeUser(String userId) {
        adminUserService.removeUser(userId);
    }

    @Override
    @Transactional
    public void updateUserRole(String userId, UpdateRoleRequestDto updateRoleRequestDto) {
        adminUserService.updateUserRole(userId, updateRoleRequestDto.getRole());
    }
}
