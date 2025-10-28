package com.mindbridge.dto.ResponseDto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T> {
    private boolean success;
    private int status;
    private String message;
    private T data;

    public ApiResponseDto(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponseDto<T> success(String message, T data) {
        return ApiResponseDto.<T>builder()
                .success(true)
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponseDto<T> success(String message) {
        return ApiResponseDto.<T>builder()
                .success(true)
                .status(200)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> ApiResponseDto<T> error(int status, String message) {
        return ApiResponseDto.<T>builder()
                .success(false)
                .status(status)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> ApiResponseDto<T> error(String message) {
        return ApiResponseDto.<T>builder()
                .success(false)
                .status(400)
                .message(message)
                .data(null)
                .build();
    }
}
