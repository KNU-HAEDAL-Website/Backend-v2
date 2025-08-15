package com.haedal.haedalweb.web.profile.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.haedal.haedalweb.application.profile.dto.ProfileRequestDto;
import com.haedal.haedalweb.application.profile.dto.ProfileResponseDto;
import com.haedal.haedalweb.application.profile.service.ProfileAppService;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.domain.user.model.JoinSemester;
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
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID, ErrorCode.BAD_REQUEST_FILE, ErrorCode.NOT_AUTHENTICATED_USER,
		ErrorCode.FORBIDDEN_UPDATE})
	@PutMapping(value = "/users/{userId}/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<SuccessResponse> updateProfileImage(@PathVariable String userId,
		@RequestPart(value = "file") MultipartFile userImageFile) {
		profileAppService.updateProfileImage(userId, userImageFile);

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_PROFILE_SUCCESS);
	}

	@Operation(summary = "프로필 이미지 삭제")
	@ApiSuccessCodeExample(SuccessCode.UPDATE_PROFILE_SUCCESS)
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID, ErrorCode.NOT_AUTHENTICATED_USER, ErrorCode.FORBIDDEN_UPDATE})
	@DeleteMapping("/users/{userId}/profile/image")
	public ResponseEntity<SuccessResponse> updateProfileImage(@PathVariable String userId) {
		profileAppService.removeProfileImage(userId);

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_PROFILE_SUCCESS);
	}

	@Operation(summary = "프로필 수정")
	@ApiSuccessCodeExample(SuccessCode.UPDATE_PROFILE_SUCCESS)
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID, ErrorCode.NOT_AUTHENTICATED_USER, ErrorCode.FORBIDDEN_UPDATE})
	@PutMapping("/users/{userId}/profile")
	public ResponseEntity<SuccessResponse> updateProfile(@PathVariable String userId,
		@Valid @RequestBody ProfileRequestDto profileRequestDto) {
		profileAppService.updateProfile(userId, profileRequestDto);

		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.UPDATE_PROFILE_SUCCESS);
	}

	// 프로필 페이징 조회 With 권한 별
	@Operation(summary = "프로필 페이징 조회")
	@Parameters({
		@Parameter(name = "page", description = "페이지 번호 (0부터 시작)", required = false, example = "0"),
		@Parameter(name = "size", description = "페이지 크기", required = false, example = "5"),
		@Parameter(name = "sort", description = "정렬 (예: name,asc). 여러 개 가능", required = false, example = "name,asc"),
		@Parameter(name = "roles", description = "조회할 역할 목록 컴마로 여러개 전달 가능 (ex: ROLE_ADMIN,ROLE_TEAM_LEADER,ROLE_MEMBER)",
			example = "ROLE_ADMIN,ROLE_TEAM_LEADER"),
		@Parameter(name = "joinSemester", description = "가입학기로 필터링 (ex: SEMESTER_2024_1)",
			required = false)
	})
	@GetMapping("/users/profiles")
	public ResponseEntity<Page<ProfileResponseDto>> getProfiles(
		@PageableDefault(size = 5, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
		@RequestParam List<Role> roles,
		@RequestParam(required = false) JoinSemester joinSemester) {

		Pageable mapped = remapSort(pageable);
		return ResponseEntity.ok(
			profileAppService.getProfilePage(roles, joinSemester, mapped));
	}

	@Operation(summary = "회원 탈퇴")
	@ApiSuccessCodeExample(SuccessCode.UPDATE_PROFILE_SUCCESS)
	@ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID, ErrorCode.NOT_AUTHENTICATED_USER, ErrorCode.FORBIDDEN_UPDATE})
	@DeleteMapping("/users/{userId}/profile")
	public ResponseEntity<SuccessResponse> cancelUserAccount(@PathVariable String userId) {
		profileAppService.expelUserAccount(userId);
		return ResponseUtil.buildSuccessResponseEntity(SuccessCode.CANCEL_USER_ACCOUNT);
	}

	private static final Map<String, String> SORT_MAP = Map.of(
		// 프론트가 보내는 키  →  JPA 경로(엔티티 기준)
		"name", "user.name",
		"role", "user.role",
		"id", "id"
	);

	private Pageable remapSort(Pageable pageable) {
		Sort sort = pageable.getSort();
		if (sort.isUnsorted()) {
			Sort defaultSort = Sort.by(Sort.Order.asc(SORT_MAP.getOrDefault("name", "user.name")));
			return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort);
		}

		Sort mapped = Sort.by(
			sort.stream()
				.map(order -> {
					String clientKey = order.getProperty();          // 예: "name"
					String mappedProp = SORT_MAP.getOrDefault(clientKey, "user.name");
					Sort.Order mappedOrder = new Sort.Order(order.getDirection(), mappedProp);
					// 문자열 정렬만 대소문자 무시
					if ("name".equals(clientKey)) {
						mappedOrder = mappedOrder.ignoreCase();
					}
					return mappedOrder;
				})
				.toList()
		);

		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), mapped);
	}
}
