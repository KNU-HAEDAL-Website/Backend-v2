package com.haedal.haedalweb.application.user.service;

import com.haedal.haedalweb.domain.association.model.UserSemester;
import com.haedal.haedalweb.domain.semester.model.Semester;
import com.haedal.haedalweb.domain.semester.repository.SemesterRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haedal.haedalweb.application.user.dto.EmailRequestDto;
import com.haedal.haedalweb.application.user.dto.EmailVerificationCodeRequestDto;
import com.haedal.haedalweb.application.user.dto.JoinRequestDto;
import com.haedal.haedalweb.domain.auth.model.VerifiedEmail;
import com.haedal.haedalweb.domain.auth.service.EmailSenderService;
import com.haedal.haedalweb.domain.auth.service.EmailVerificationService;
import com.haedal.haedalweb.domain.auth.service.VerifiedEmailService;
import com.haedal.haedalweb.domain.profile.service.ProfileService;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.domain.user.service.JoinService;
import com.haedal.haedalweb.util.EmailUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinAppServiceImpl implements JoinAppService {
	private final JoinService joinService;
	private final ProfileService profileService;
	private final EmailVerificationService emailVerificationService;
	private final VerifiedEmailService verifiedEmailService;
	private final EmailSenderService emailSenderService;
	private final PasswordEncoder passwordEncoder;
	private final SemesterRepository semesterRepository;

	@Transactional
	@Override
	public void registerUserAccount(JoinRequestDto joinRequestDto) {
		// 인증된 이메일인지 확인
		verifiedEmailService.validateVerifiedEmail(joinRequestDto.getUserId(), joinRequestDto.getEmail());

		// 일반 멤버로 설정
		User user = registerUserFromDto(joinRequestDto, Role.ROLE_MEMBER, UserStatus.INACTIVE);

		// 등록
//		joinService.registerAccount(user);
		profileService.generateProfile(user);
	}

	@Transactional
	@Override
	public void registerMasterAccount(JoinRequestDto joinRequestDto) { // 개발용
		// 인증된 이메일인지 확인
		verifiedEmailService.validateVerifiedEmail(joinRequestDto.getUserId(), joinRequestDto.getEmail());

		// 웹 관리자로 설정
		User user = registerUserFromDto(joinRequestDto, Role.ROLE_WEB_MASTER, UserStatus.MASTER);

		// 등록
		joinService.registerAccount(user);
		profileService.generateProfile(user);
	}

	@Transactional(readOnly = true)
	@Override
	public void validateUserIdDuplicate(String userId) {
		joinService.validateUserId(userId);
	}

	@Transactional(readOnly = true)
	@Override
	public void validateStudentNumberDuplicate(Integer studentNumber) {
		joinService.validateStudentNumber(studentNumber);
	}

	@Transactional
	@Override
	public void registerAndSendEmailVerification(EmailRequestDto emailRequestDto) {
		String email = emailRequestDto.getEmail();

		// 이메일 중복 검사
		joinService.validateEmail(email);

		// 인증 코드 생성 후 저장
		String code = EmailUtil.generateVerificationCode();
		emailVerificationService.registerEmailVerification(email, code);

		// 인증 코드 전송
		emailSenderService.sendVerificationEmail(email, code);
	}

	@Transactional
	@Override
	public void validateCode(EmailVerificationCodeRequestDto emailVerificationCodeRequestDto) {
		// 인증 코드 검증
		emailVerificationService.validateCode(emailVerificationCodeRequestDto.getEmail(),
			emailVerificationCodeRequestDto.getCode());

		// 인증 여부 저장 (특정 ID로 특정 Email 인증 여부)
		verifiedEmailService.registerVerifiedEmail(
			VerifiedEmail.builder()
				.userId(emailVerificationCodeRequestDto.getUserId())
				.email(emailVerificationCodeRequestDto.getEmail())
				.build()
		);
	}

	private User registerUserFromDto(JoinRequestDto dto, Role role, UserStatus userStatus) {
		User user = User.builder()
			.id(dto.getUserId())
			.email(dto.getEmail())
			.password(passwordEncoder.encode(dto.getPassword()))
			.name(dto.getUserName())
			.role(role)
			.userStatus(userStatus)
			.studentNumber(dto.getStudentNumber())
			.build();

		List<Semester> semesterList = semesterRepository.findSemestersFrom(dto.getSemester());
		if (semesterList.isEmpty()) {
			throw new IllegalArgumentException("No semesters found starting from: " + dto.getSemester());
		}

		joinService.registerAccount(user);

		for (Semester semester : semesterList) {
			UserSemester userSemester = UserSemester.builder()
					.user(user)
					.semester(semester)
					.build();
			user.getUserSemesters().add(userSemester);
			semester.getUserSemesters().add(userSemester);
		}

		return user;
	}
}
