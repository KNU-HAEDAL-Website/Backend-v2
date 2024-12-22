package com.haedal.haedalweb.web.profile.controller;

import com.haedal.haedalweb.application.profile.dto.ProfileResponseDto;
import com.haedal.haedalweb.application.profile.service.ProfileAppService;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.swagger.ApiErrorCodeExamples;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExample;
import com.haedal.haedalweb.util.ResponseUtil;
import com.haedal.haedalweb.web.common.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "프로필 API")
@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final ProfileAppService profileAppService;

    // 유저 프로필 조회
    @Operation(summary = "프로필 단일 조회")
    @GetMapping("/users/{userId}/profile")
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID})
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable String userId) {
        return ResponseEntity.ok(profileAppService.getProfile(userId));
    }

    @Operation(summary = "프로필 이미지 수정")
    @ApiSuccessCodeExample(SuccessCode.UPDATE_PROFILE_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID, ErrorCode.NOT_AUTHENTICATED_USER, ErrorCode.FORBIDDEN_UPDATE})
    // 프로필 이미지 수정, ApiSuccess & ApiError 작성하기
    @PutMapping(value = "/users/{userId}/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> updateProfileImage(@PathVariable String userId, @RequestPart(value = "file") MultipartFile userImageFile) {
        profileAppService.updateProfileImage(userId, userImageFile);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_PROFILE_SUCCESS);
    }

//    @Operation(summary = "프로필 수정")
//    @ApiSuccessCodeExample(SuccessCode.UPDATE_PROFILE_SUCCESS)
//
//    @PutMapping("/users/{userId}/profile")
//    public ResponseEntity<SuccessResponse> updateProfile(@PathVariable String userId) {
//        profileAppService.updateProfile(userId);
//
//        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_PROFILE_SUCCESS);
//    }

//
//    // 모든 유저 프로필 목록 조회
//    @GetMapping("/users/profiles")
//    public ResponseEntity<> getProfiles() {
//    }

}
