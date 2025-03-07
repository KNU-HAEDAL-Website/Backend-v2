package com.haedal.haedalweb.exception;

import java.io.IOException;

import org.springframework.web.filter.GenericFilterBean;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.util.ResponseUtil;
import com.haedal.haedalweb.web.common.dto.ErrorResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilterExceptionHandler extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
	}

	private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		try {
			chain.doFilter(request, response);
		} catch (BusinessException e) {
			sendErrorResponse(response, e);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	private void sendErrorResponse(HttpServletResponse response, BusinessException e) {
		ErrorCode errorCode = e.getErrorCode();
		response.setStatus(errorCode.getHttpStatus().value());
		ErrorResponse errorResponse = ErrorResponse.builder()
			.code(errorCode.getCode())
			.message(errorCode.getMessage())
			.build();

		ResponseUtil.writeAsJsonResponse(response, errorResponse);
	}
}
