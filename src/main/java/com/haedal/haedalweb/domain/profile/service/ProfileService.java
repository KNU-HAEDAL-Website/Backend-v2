package com.haedal.haedalweb.domain.profile.service;

import com.haedal.haedalweb.domain.profile.model.Profile;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProfileService {
    void generateProfile(User user);
    Profile getProfileWithImageAndUser(String userId);

    Profile getProfileWithUser(String userId);
    Page<Profile> getProfilePage(List<Role> roles, Pageable pageable);
    void validateAuthorityOfProfileManagement(String userId, User loggedInUser);
}
