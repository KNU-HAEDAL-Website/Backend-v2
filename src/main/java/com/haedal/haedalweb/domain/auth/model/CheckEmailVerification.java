package com.haedal.haedalweb.domain.auth.model;

import com.haedal.haedalweb.constants.EmailConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@RedisHash(value = EmailConstants.CHECK_EMAIL_VERIFICATION, timeToLive = EmailConstants.CHECK_EMAIL_CODE_EXPIRATION_TIME_S)
public class CheckEmailVerification {
    @Id
    private String userId;

    private String email;
}
