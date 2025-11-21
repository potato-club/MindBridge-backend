package com.mindbridge.dto;

public record ApiResponseDto<T> (
    boolean success,
    String message,
    T data
) {
    public static <T> ApiResponseDto<T> success(String message, T data) {
        return new ApiResponseDto<>(true, message, data);
    }

    public static <T> ApiResponseDto<T> error(String message) {
        return new ApiResponseDto<>(false, message, null);
    }
}