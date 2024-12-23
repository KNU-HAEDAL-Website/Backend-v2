package com.haedal.haedalweb.web.user.controller;

import com.haedal.haedalweb.application.user.service.UserAppService;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.application.user.dto.UserResponseDto;
import com.haedal.haedalweb.swagger.ApiErrorCodeExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "유저 API")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserAppService userAppService;

//    @Operation(summary = "User Me 정보 조회")
//    @ApiErrorCodeExamples({ErrorCode.NOT_FOUND_USER_ID})
//    @GetMapping("/users/me")
//    public ResponseEntity<UserResponseDto> getMe() {
//        return ResponseEntity.ok(userAppService.getMe());
//    }

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
        return ResponseEntity.ok(userAppService.getUsers(Sort.by(Sort.Order.asc("name"), Sort.Order.asc("studentNumber"))));
    }

}
//    @Operation(summary = "User 목록 (학번 포함)")
//    @GetMapping("/private/users")
//    public ResponseEntity<List<UserSummaryResponseDto>> getUser(){
//        List<UserSummaryResponseDto> users = userService.getUsers();
//
//        return ResponseEntity.ok(users);
//    }

    //    @Operation(summary = "User 목록")
//    @Parameters({
//            @Parameter(name = "page", description = "현재 페이지"),
//            @Parameter(name = "size", description = "한 페이지에 노출할 데이터 수")
//    })
//    @GetMapping
//    public ResponseEntity<Page<UserResponseDto>> getUser(@RequestParam(value = "page", defaultValue = "0") int page,
//                                                      @RequestParam(value = "size", defaultValue = "5") int size){
//        Page<UserResponseDto> activeUsers;
//        activeUsers = userService.getUsers(PageRequest.of(page, size, Sort.by(Order.asc("role"), Order.asc("name"))));
//
//        return ResponseEntity.ok(activeUsers);
//    }
