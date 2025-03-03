package com.haedal.haedalweb.infrastructure.email.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.haedal.haedalweb.domain.auth.service.EmailSenderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
	private final JavaMailSender javaMailSender;

	@Override
	@Async
	public void sendVerificationEmail(String to, String code) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("이메일 인증 코드");
		message.setText("인증 코드: " + code + "\n3분 내에 입력해주세요.");
		javaMailSender.send(message);
	}

	@Override
	@Async
	public void sendRandomPassword(String to, String password) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("비밀번호 초기화");
		message.setText("랜덤 비밀번호: " + password + "\n3분 내에 입력해주세요.");
		javaMailSender.send(message);
	}
}
