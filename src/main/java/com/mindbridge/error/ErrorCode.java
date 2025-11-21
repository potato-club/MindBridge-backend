package com.mindbridge.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-500","서버 내부 오류가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON-400", "잘못된 요청 데이터입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "COMMON-403", "접근 권한이 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON-401", "인증이 필요합니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-404", "계정을 찾을 수 없습니다."),
    DUPLICATE_USERID(HttpStatus.CONFLICT, "USER-409", "이미 사용 중인 아이디입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "USER-4092", "이미 사용 중인 닉네임입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "USER-4001", "비밀번호가 일치하지 않습니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-4011", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-4012", "토큰이 만료되었습니다."),
    LOGOUT_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-4013", "이미 로그아웃 처리된 토큰입니다."),

    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "PW-4001", "새 비밀번호가 일치하지 않습니다."),
    PASSWORD_POLICY_VIOLATED(HttpStatus.BAD_REQUEST, "PW-4002", "비밀번호 형식이 올바르지 않습니다."),
    PASSWORD_TOO_SHORT(HttpStatus.BAD_REQUEST, "AUTH-4001", "비밀번호는 8자 이상이어야 합니다."),
    PASSWORD_REGEX_NOT_VALID(HttpStatus.BAD_REQUEST, "AUTH-4002", "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다."),
    PASSWORD_SAME_AS_OLD(HttpStatus.BAD_REQUEST, "AUTH-4003", "이전 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
