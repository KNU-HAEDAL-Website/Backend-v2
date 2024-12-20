package com.haedal.haedalweb.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode implements ResponseCode{
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON_001", "Parameter is invalid."),
    FORBIDDEN_UPDATE(HttpStatus.FORBIDDEN, "COMMON_002", "수정, 삭제 권한이 없습니다."),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "COMMON_003", "인자 값이 누락되었거나 잘못된 형식입니다."),
    NOT_SAVE_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_004", "파일을 저장하지 못했습니다."),
    BAD_REQUEST_FILE(HttpStatus.BAD_REQUEST, "COMMON_005", "잘못된 파일 형식입니다."),
    
    INVALID_LOGIN_CONTENTS_TYPE(HttpStatus.BAD_REQUEST, "AUTH_001", "지원하지 않는 형식입니다."),
    FAILED_LOGIN(HttpStatus.UNAUTHORIZED, "AUTH_002", "아이디 또는 비밀번호가 일치하지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_003", "Access Token has expired."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_004", "Access Token is invalid."),
    NULL_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "AUTH_005", "Refresh Token is null."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "AUTH_006", "Refresh Token has expired."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "AUTH_007", "Refresh Token is invalid."),

    DUPLICATED_USER_ID(HttpStatus.CONFLICT, "USER_001", "중복된 아이디가 존재합니다."),
    DUPLICATED_STUDENT_NUMBER(HttpStatus.CONFLICT, "USER_002", "중복된 학번이 존재합니다."),
    NOT_FOUND_USER_ID(HttpStatus.NOT_FOUND, "USER_003", "유저 아이디를 찾을 수 없습니다."),
    NOT_FOUND_ROLE(HttpStatus.NOT_FOUND, "USER_004", "해당하는 권한을 찾을 수 없습니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "USER_005", "중복된 이메일이 존재합니다."),

    DUPLICATED_SEMESTER(HttpStatus.CONFLICT, "SEMESTER_001", "이미 해당 학기가 존재합니다."),
    NOT_FOUND_SEMESTER_ID(HttpStatus.NOT_FOUND, "SEMESTER_002", "해당 학기를 찾을 수 없습니다."),
    EXIST_ACTIVITY(HttpStatus.CONFLICT, "SEMESTER_003", "해당 학기에 활동이 존재하는 경우 삭제할 수 없습니다."),

    NOT_FOUND_ACTIVITY_ID(HttpStatus.NOT_FOUND, "ACTIVITY_001", "해당 활동을 찾을 수 없습니다."),
    EXIST_BOARD(HttpStatus.CONFLICT, "ACTIVITY_002", "해당 활동에 게시판이 존재하는 경우 삭제할 수 없습니다."),

    NOT_FOUND_BOARD_ID(HttpStatus.NOT_FOUND, "BOARD_001", "해당 게시판을 찾을 수 없습니다."),
    EXIST_POST(HttpStatus.CONFLICT, "BOARD_002", "해당 게시판에 게시글이 존재하는 경우 삭제할 수 없습니다."),

    NOT_FOUND_POST_TYPE(HttpStatus.NOT_FOUND, "POST_001", "해당 게시글 타입이 존재하지 않습니다."),
    NOT_FOUND_POST_ID(HttpStatus.NOT_FOUND, "POST_002", "해당 게시글을 찾을 수 없습니다."),

    INVALID_EMAIL_VERIFICATION(HttpStatus.BAD_REQUEST, "EMAIL_001", "인증 코드가 만료되었거나 일치하지 않습니다."),
    LIMIT_EXCEEDED_SEND_EMAIL(HttpStatus.TOO_MANY_REQUESTS, "EMAIL_002", "해당 이메일의 전송 및 재전송 횟수가 3회를 초과 했습니다."),
    NOT_FOUND_CHECK_EMAIL_VERIFICATION(HttpStatus.NOT_FOUND, "EMAIL_003", "이메일이 인증되지 않았습니다."),
    NOT_AUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "EMAIL_004", "인증 되지 않은 사용자입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
