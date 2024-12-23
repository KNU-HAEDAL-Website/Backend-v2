package com.haedal.haedalweb.web.profile.controller;

import com.haedal.haedalweb.application.profile.dto.ProfileRequestDto;
import com.haedal.haedalweb.application.profile.dto.ProfileResponseDto;
import com.haedal.haedalweb.application.profile.service.ProfileAppService;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.swagger.ApiErrorCodeExamples;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExample;
import com.haedal.haedalweb.util.ResponseUtil;
import com.haedal.haedalweb.web.common.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    @PutMapping(value = "/users/{userId}/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> updateProfileImage(@PathVariable String userId, @RequestPart(value = "file") MultipartFile userImageFile) {
        profileAppService.updateProfileImage(userId, userImageFile);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_PROFILE_SUCCESS);
    }

    @Operation(summary = "프로필 수정")
    @ApiSuccessCodeExample(SuccessCode.UPDATE_PROFILE_SUCCESS)
    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID, ErrorCode.NOT_AUTHENTICATED_USER, ErrorCode.FORBIDDEN_UPDATE})
    @PutMapping("/users/{userId}/profile")
    public ResponseEntity<SuccessResponse> updateProfile(@PathVariable String userId, @Valid @RequestBody ProfileRequestDto profileRequestDto) {
        profileAppService.updateProfile(userId, profileRequestDto);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_PROFILE_SUCCESS);
    }

    // 프로필 페이징 조회 With 권한 별
    @Operation(summary = "프로필 페이징 조회")
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", required = false, example = "0"),
            @Parameter(name = "size", description = "페이지 크기", required = false, example = "5"),
            @Parameter(name = "roles", description = "조회할 역할 목록 컴마로 여러개 전달 가능 (ex: ROLE_ADMIN,ROLE_TEAM_LEADER,ROLE_MEMBER)", required = false, example = "ROLE_ADMIN,ROLE_TEAM_LEADER")
    })
    @GetMapping("/users/profiles")
    public ResponseEntity<Page<ProfileResponseDto>> getProfiles(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                @RequestParam(name = "size", defaultValue = "5") Integer size,
                                                                @RequestParam List<Role> roles) {
        return ResponseEntity.ok(profileAppService.getProfilePage(roles, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id")))));
    }

}
