package com.haedal.haedalweb.domain.user.service;

import java.util.List;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getUsersByUserStatus(UserStatus userStatus, Sort sort) {
        return userRepository.findByUserStatus(userStatus, sort);
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_ID));

        if (user.getUserStatus() != UserStatus.ACTIVE && user.getUserStatus() != UserStatus.MASTER) {
            throw new BusinessException(ErrorCode.NOT_FOUND_USER_ID);
        }

        return user;
    }

    @Override
    public List<User> getUsersByIds(List<String> userIds) {
        return userRepository.findAllById(userIds);
    }

    @Override
    public void validateActiveUsers(List<String> userIds, List<User> users) {
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

    @Override
    public void cancelUserAccount(User loggedInUser) {
        if (loggedInUser.getUserStatus() != UserStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.NOT_FOUND_USER_ID);
        }

        loggedInUser.setUserStatus(UserStatus.DELETED);
        loggedInUser.setEmail(null);
        loggedInUser.setStudentNumber(null);
    }
}
