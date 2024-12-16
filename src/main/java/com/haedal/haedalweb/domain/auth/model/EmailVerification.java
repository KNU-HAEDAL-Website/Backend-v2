package com.haedal.haedalweb.domain.auth.model;

import com.haedal.haedalweb.constants.EmailConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@RedisHash(value = EmailConstants.EMAIL_VERIFICATION, timeToLive = EmailConstants.EMAIL_CODE_EXPIRATION_TIME_S)
public class EmailVerification {
    @Id
    private String email;

    private String code;

    private int resendCount;
}
