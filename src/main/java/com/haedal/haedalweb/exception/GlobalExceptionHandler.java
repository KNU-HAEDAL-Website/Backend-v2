package com.haedal.haedalweb.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.web.common.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		ErrorCode errorCode = e.getErrorCode();
		ErrorResponse errorResponse = ErrorResponse.builder()
			.code(errorCode.getCode())
			.message(errorCode.getMessage())
			.build();

		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidException(MethodArgumentNotValidException e) {
		ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
		return handleExceptionInternal(e, errorCode);
	}

	private ResponseEntity<ErrorResponse> handleExceptionInternal(MethodArgumentNotValidException e,
		ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(makeErrorResponse(e, errorCode));
	}

	private ErrorResponse makeErrorResponse(MethodArgumentNotValidException e, ErrorCode errorCode) {
		List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(ErrorResponse.ValidationError::of)
			.collect(Collectors.toList());

		return ErrorResponse.builder()
			.message(errorCode.getMessage())
			.code(errorCode.getCode())
			.errors(validationErrorList)
			.build();
	}
}
