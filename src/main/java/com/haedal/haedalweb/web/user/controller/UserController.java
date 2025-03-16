package com.haedal.haedalweb.web.user.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haedal.haedalweb.application.user.dto.FindUserIdResponseDto;
import com.haedal.haedalweb.application.user.dto.ResetPasswordEmailCodeRequestDto;
import com.haedal.haedalweb.application.user.dto.ResetPasswordRequestDto;
import com.haedal.haedalweb.application.user.dto.UpdatePasswordRequestDto;
import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import com.haedal.haedalweb.application.user.service.UserAppService;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.swagger.ApiErrorCodeExample;
import com.haedal.haedalweb.swagger.ApiErrorCodeExamples;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExample;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExamples;
import com.haedal.haedalweb.util.ResponseUtil;
import com.haedal.haedalweb.web.common.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저 API")
@RequiredArgsConstructor
@RestController
public class UserController {
	private final UserAppService userAppService;

	@Operation(summary = "User 조회 (학번 포함, 회원만)")
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID})
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserResponseDto> getUser(@PathVariable String userId) {
		return ResponseEntity.ok(userAppService.getUser(userId));
	}

	@Operation(summary = "User 목록 조회 (학번 포함, 회원만)")
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID})
	@GetMapping("/users")
	public ResponseEntity<List<UserResponseDto>> getUsers() {
		return ResponseEntity.ok(
			userAppService.getUsers(Sort.by(Sort.Order.asc("name"), Sort.Order.asc("studentNumber"))));
	}

	@Operation(summary = "학기별 활동 User 목록 조회 (학번 포함)")
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID})
	@GetMapping("/users/semester/{semesterName}")
	public ResponseEntity<List<UserResponseDto>> getUsersBySemester(@PathVariable String semesterName) {
		return ResponseEntity.ok(
				userAppService.getUsersBySemester(semesterName, Sort.by(Sort.Order.asc("name"), Sort.Order.asc("studentNumber"))));
	}

	@Operation(summary = "회원 탈퇴")
	@ApiSuccessCodeExample(SuccessCode.CANCEL_USER_ACCOUNT)
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID, ErrorCode.NOT_AUTHENTICATED_USER})
	@DeleteMapping("/users/me")
	public ResponseEntity<SuccessResponse> cancelUserAccount() {
		userAppService.cancelUserAccount();

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.CANCEL_USER_ACCOUNT);
	}

	@Operation(summary = "아이디 찾기")
	@ApiErrorCodeExample(ErrorCode.NOT_FOUND_USER_ID)
	@GetMapping("/users/find-id")
	public ResponseEntity<FindUserIdResponseDto> getUserId(@RequestParam Integer studentNumber,
		@RequestParam String name) {
		return ResponseEntity.ok(userAppService.getUserId(studentNumber, name));
	}

	@Operation(summary = "비밀번호 찾기 (인증코드 전송)")
	@ApiSuccessCodeExample(SuccessCode.SEND_VERIFICATION_CODE_SUCCESS)
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID, ErrorCode.LIMIT_EXCEEDED_SEND_EMAIL})
	@PostMapping("/users/password/reset")
	public ResponseEntity<SuccessResponse> resetPassword(@RequestBody @Valid ResetPasswordRequestDto resetPasswordRequestDto) {
		userAppService.resetPassword(resetPasswordRequestDto);

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.SEND_VERIFICATION_CODE_SUCCESS);
	}

	@Operation(summary = "비밀번호 찾기 (인증코드 확인 및 랜덤 비밀번호 전송)")
	@ApiSuccessCodeExamples({SuccessCode.VERIFY_VERIFICATION_CODE_SUCCESS})
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID, ErrorCode.INVALID_EMAIL_VERIFICATION})
	@PatchMapping("/users/password/verify")
	public ResponseEntity<SuccessResponse> verifyResetPasswordCode(
		@RequestBody @Valid ResetPasswordEmailCodeRequestDto resetPasswordEmailCodeRequestDto) {
		userAppService.verifyResetPasswordCode(resetPasswordEmailCodeRequestDto);

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.VERIFY_VERIFICATION_CODE_SUCCESS);
	}

	@Operation(summary = "비밀번호 변경 (프로필 페이지)")
	@ApiSuccessCodeExample(SuccessCode.UPDATE_USER_PASSWORD)
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID, ErrorCode.BAD_REQUEST_PASSWORD})
	@PatchMapping("/users/me/password")
	public ResponseEntity<SuccessResponse> updateUserPassword(@RequestBody @Valid UpdatePasswordRequestDto updatePasswordRequestDto) {
		userAppService.updatePassword(updatePasswordRequestDto);

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_USER_PASSWORD);
	}
}
