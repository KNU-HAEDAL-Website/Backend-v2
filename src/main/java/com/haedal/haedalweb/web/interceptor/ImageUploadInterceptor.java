package com.haedal.haedalweb.web.interceptor;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.util.ImageValidationUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class ImageUploadInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 해당 요청이 multipart 타입인지 확인
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

            MultipartFile file = multipartRequest.getFile("file");
            if (file != null && !file.isEmpty()) {
                // 이미지 검증 로직
                ImageValidationUtil.validateImageExtension(file);
            } else {
                // 파일이 없거나 비어있는 경우 예외 처리
                throw new BusinessException(ErrorCode.BAD_REQUEST_FILE);
            }
        }

        return true; // true를 반환해야 컨트롤러로 요청이 전달됨
    }
}