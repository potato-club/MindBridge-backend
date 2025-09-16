package com.mindbridge.global;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.NoSuchElementException;


@RestControllerAdvice

public class GlobalExceptionHandler {

    public record ErrorResponse(String code, String message, int status) {}


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> notFound(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("U001", e.getMessage(), 404)); // 사용자 없음
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> badRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("C001", e.getMessage(), 400)); // 잘못된 요청
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> serverError(Exception e) {
        // 필요하면 e.printStackTrace()나 로깅 추가
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("S001", "서버 오류가 발생했습니다.", 500));
    }
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> badJson(HttpMessageNotReadableException e) {
        return ResponseEntity.status(400).body(new ErrorResponse("C002", "요청 본문(JSON)을 읽을 수 없습니다.", 400));
    }


}
