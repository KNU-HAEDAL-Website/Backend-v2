package com.haedal.haedalweb.infrastructure;

public interface EmailSenderService {
    void sendVerificationEmail(String to, String code);
}
