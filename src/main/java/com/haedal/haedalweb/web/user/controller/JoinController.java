package com.haedal.haedalweb.web.user.controller;

import com.haedal.haedalweb.application.user.dto.EmailRequestDto;
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
import com.haedal.haedalweb.domain.user.service.JoinService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/join")
public class JoinController {
    private final JoinService joinService;
    private final JoinAppService joinAppService;

    @Operation(summary = "회원가입")
    @ApiSuccessCodeExample(SuccessCode.JOIN_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.DUPLICATED_USER_ID, ErrorCode.DUPLICATED_STUDENT_NUMBER, ErrorCode.INVALID_PARAMETER})
    @PostMapping
    public ResponseEntity<SuccessResponse> resisterUser(@RequestBody @Valid JoinRequestDto joinRequestDTO) {
        joinService.createUserAccount(joinRequestDTO);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.JOIN_SUCCESS);
    }

    @Operation(summary = "관리자 회원가입 (개발용)")
    @ApiSuccessCodeExample(SuccessCode.JOIN_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.DUPLICATED_USER_ID, ErrorCode.DUPLICATED_STUDENT_NUMBER, ErrorCode.INVALID_PARAMETER})
    @PostMapping("/admin")
    public ResponseEntity<SuccessResponse> resisterAdmin(@RequestBody @Valid JoinRequestDto joinRequestDTO) {
        joinService.createAdminAccount(joinRequestDTO);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.JOIN_SUCCESS);
    }

    @Operation(summary = "ID 중복확인")
    @Parameter(name = "userId", description = "중복 확인할 ID")
    @ApiSuccessCodeExamples({SuccessCode.UNIQUE_USER_ID, SuccessCode.DUPLICATED_USER_ID})
    @GetMapping("/check-user-id")
    public ResponseEntity<SuccessResponse> checkUserIdDuplicate(@RequestParam String userId) {
        SuccessCode successCode = SuccessCode.UNIQUE_USER_ID;
        if (joinService.isUserIdDuplicate(userId)) {
            successCode = SuccessCode.DUPLICATED_USER_ID;
        }

        return ResponseUtil.buildSuccessResponseEntity(successCode);
    }

    @Operation(summary = "학번 중복확인")
    @Parameter(name = "studentNumber", description = "중복 확인할 학번")
    @ApiSuccessCodeExamples({SuccessCode.UNIQUE_STUDENT_NUMBER, SuccessCode.DUPLICATED_STUDENT_NUMBER})
    @GetMapping("/check-student-number")
    public ResponseEntity<SuccessResponse> checkStudentNumberDuplicate(@RequestParam Integer studentNumber) {
        SuccessCode successCode = SuccessCode.UNIQUE_STUDENT_NUMBER;
        if (joinService.isStudentNumberDuplicate(studentNumber)) {
            successCode = SuccessCode.DUPLICATED_STUDENT_NUMBER;
        }

        return ResponseUtil.buildSuccessResponseEntity(successCode);
    }

    @Operation(summary = "이메일 인증 코드 전송")
    @ApiSuccessCodeExamples({SuccessCode.SEND_VERIFICATION_CODE_SUCCESS})
    @PostMapping("/email/send")
    public ResponseEntity<SuccessResponse> sendVerificationCode(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        joinAppService.createAndSendVerificationCode(emailRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.SEND_VERIFICATION_CODE_SUCCESS);
    }
}
