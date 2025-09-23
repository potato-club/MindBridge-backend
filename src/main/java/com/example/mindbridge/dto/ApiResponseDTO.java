package com.example.mindbridge.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponseDTO<T> success(String message, T data) {
        return new ApiResponseDTO<>(true, message, data);
    }
}
