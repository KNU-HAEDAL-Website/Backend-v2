package com.haedal.haedalweb.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.security.dto.CustomUserDetails;
import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import com.haedal.haedalweb.application.user.dto.UserSummaryResponseDto;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserSummaryResponseDto> getUsers() {
        List<User> users = userRepository.findByUserStatus(UserStatus.ACTIVE, null);

        return users.stream()
                .map(this::convertToPrivateUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getMe() {
        User user = getLoggedInUser();

        return convertToUserDTO(user);
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_ID));
    }

    @Override
    public List<User> getUsersByIds(List<String> userIds) {
        return userRepository.findAllById(userIds);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();

        return getUser(userId);
    }

    @Override
    public UserResponseDto convertToUserDTO(User user) {
        return UserResponseDto.builder()
                .userId(user.getId())
                .studentNumber(user.getStudentNumber())
                .userName(user.getName())
                .role(user.getRole().getLabel())
                .regDate(user.getRegDate())
                .build();
    }

    @Override
    public void validateActiveUsers(List<User> users, List<String> userIds) {
        if (users.size() != userIds.size()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_USER_ID);
        }

        users.forEach(user -> {
            UserStatus userStatus = user.getUserStatus();
            if (userStatus != UserStatus.ACTIVE) {
                throw new BusinessException(ErrorCode.NOT_FOUND_USER_ID);
            }
        });
    }

    private UserSummaryResponseDto convertToPrivateUserDTO(User user) {
        return UserSummaryResponseDto.builder()
                .userId(user.getId())
                .studentNumber(user.getStudentNumber())
                .userName(user.getName())
                .build();
    }
}
