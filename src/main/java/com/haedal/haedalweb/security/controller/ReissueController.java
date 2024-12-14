package com.haedal.haedalweb.security.controller;

import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.security.service.AuthApplicationService;
import com.haedal.haedalweb.swagger.ApiSuccessCodeExample;
import com.haedal.haedalweb.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "토큰 재발급")
@RequiredArgsConstructor
@RestController
public class ReissueController {
    private final AuthApplicationService authApplicationService;

    @Operation(summary = "JWT 재발급")
    @ApiSuccessCodeExample(SuccessCode.REISSUE_SUCCESS)
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        authApplicationService.reissueToken(request, response);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.REISSUE_SUCCESS);
    }
}
