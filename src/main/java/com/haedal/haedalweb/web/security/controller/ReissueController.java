package com.haedal.haedalweb.web.security.controller;

import com.haedal.haedalweb.constants.SuccessCode;
import com.haedal.haedalweb.security.service.TokenAppService;
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

@Tag(name = "토큰 관련 API")
@RequiredArgsConstructor
@RestController
public class ReissueController {
    private final TokenAppService tokenAppService;

    @Operation(summary = "JWT 재발급")
    @ApiSuccessCodeExample(SuccessCode.REISSUE_SUCCESS)
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        tokenAppService.reissueToken(request, response);

        return ResponseUtil.buildSuccessResponseEntity(SuccessCode.REISSUE_SUCCESS);
    }
}
