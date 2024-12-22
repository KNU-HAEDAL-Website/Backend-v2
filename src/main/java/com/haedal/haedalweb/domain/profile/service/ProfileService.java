package com.haedal.haedalweb.domain.profile.service;

import com.haedal.haedalweb.domain.profile.model.Profile;
import com.haedal.haedalweb.domain.user.model.User;

public interface ProfileService {
    void generateProfile(User user);
    Profile getProfileWithImageAndUser(String userId);

    void validateAuthorityOfProfileManagement(String userId, User loggedInUser);
}
