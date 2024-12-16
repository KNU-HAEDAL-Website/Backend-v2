package com.haedal.haedalweb.constants;

public final class EmailConstants {
    public static final long EMAIL_CODE_EXPIRATION_TIME_S = 3*60; // 3 min
    public static final long CHECK_EMAIL_CODE_EXPIRATION_TIME_S = 10*60; // 10 min
    public static final String EMAIL_VERIFICATION = "emailVerification";
    public static final String CHECK_EMAIL_VERIFICATION = "checkEmailVerification";

    public static final String CODE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static final int CODE_LENGTH = 6;

    public static final int RESEND_LIMIT = 3;
}
