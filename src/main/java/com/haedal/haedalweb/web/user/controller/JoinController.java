package com.haedal.haedalweb.web.user.controller;

import com.haedal.haedalweb.application.user.dto.EmailRequestDto;
import com.haedal.haedalweb.application.user.dto.EmailVerificationCodeRequestDto;
import com.haedal.haedalweb.application.user.service.JoinAppService;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.application.user.dto.JoinRequestDto;
import com.haedal.haedalweb.web.common.dto.SuccessResponse;
import com.haedal.haedalweb.swagger.ApiErrorCodeExamples;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExample;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExamples;
import com.haedal.haedalweb.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/join")
public class JoinController {
    private final JoinAppService joinAppService;

    @Operation(summary = "회원가입")
    @ApiSuccessCodeExample(SuccessCode.JOIN_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.DUPLICATED_USER_ID, ErrorCode.DUPLICATED_STUDENT_NUMBER, ErrorCode.DUPLICATED_EMAIL, ErrorCode.NOT_FOUND_CHECK_EMAIL_VERIFICATION})
    @PostMapping
    public ResponseEntity<SuccessResponse> resisterUser(@RequestBody @Valid JoinRequestDto joinRequestDto) {
        joinAppService.createUserAccount(joinRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.JOIN_SUCCESS);
    }

    @Operation(summary = "관리자 회원가입 (개발용)")
    @ApiSuccessCodeExample(SuccessCode.JOIN_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.DUPLICATED_USER_ID, ErrorCode.DUPLICATED_STUDENT_NUMBER, ErrorCode.DUPLICATED_EMAIL, ErrorCode.NOT_FOUND_CHECK_EMAIL_VERIFICATION})
    @PostMapping("/admin")
    public ResponseEntity<SuccessResponse> resisterAdmin(@RequestBody @Valid JoinRequestDto joinRequestDTO) {
        joinAppService.createMasterAccount(joinRequestDTO);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.JOIN_SUCCESS);
    }

    @Operation(summary = "ID 중복확인")
    @Parameter(name = "userId", description = "중복 확인할 ID")
    @ApiSuccessCodeExamples({SuccessCode.UNIQUE_USER_ID})
    @ApiErrorCodeExamples({ErrorCode.DUPLICATED_USER_ID})
    @GetMapping("/check-user-id")
    public ResponseEntity<SuccessResponse> checkUserIdDuplicate(@RequestParam String userId) {
        joinAppService.checkUserIdDuplicate(userId);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UNIQUE_USER_ID);
    }

    @Operation(summary = "학번 중복확인")
    @Parameter(name = "studentNumber", description = "중복 확인할 학번")
    @ApiSuccessCodeExamples({SuccessCode.UNIQUE_STUDENT_NUMBER})
    @ApiErrorCodeExamples({ErrorCode.DUPLICATED_STUDENT_NUMBER})
    @GetMapping("/check-student-number")
    public ResponseEntity<SuccessResponse> checkStudentNumberDuplicate(@RequestParam Integer studentNumber) {
        joinAppService.checkStudentNumberDuplicate(studentNumber);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UNIQUE_STUDENT_NUMBER);
    }

    @Operation(summary = "이메일 인증 코드 전송/재전송")
    @ApiSuccessCodeExamples({SuccessCode.SEND_VERIFICATION_CODE_SUCCESS})
    @ApiErrorCodeExamples({ErrorCode.DUPLICATED_EMAIL, ErrorCode.LIMIT_EXCEEDED_SEND_EMAIL})
    @PostMapping("/email/send")
    public ResponseEntity<SuccessResponse> sendVerificationCode(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        joinAppService.createAndSendVerificationCode(emailRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.SEND_VERIFICATION_CODE_SUCCESS);
    }

    @Operation(summary = "이메일 인증 코드 검증")
    @ApiSuccessCodeExamples({SuccessCode.VERIFY_VERIFICATION_CODE_SUCCESS})
    @ApiErrorCodeExamples({ErrorCode.INVALID_EMAIL_VERIFICATION})
    @PostMapping("/email/verify")
    public ResponseEntity<SuccessResponse> verifyCode(@RequestBody @Valid EmailVerificationCodeRequestDto emailVerificationCodeRequestDto) {
        joinAppService.verifyCode(emailVerificationCodeRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.VERIFY_VERIFICATION_CODE_SUCCESS);
    }
}
