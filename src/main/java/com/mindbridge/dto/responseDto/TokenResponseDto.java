package com.mindbridge.dto.responseDto;

public record TokenResponseDto(
        String accessToken,
        String refreshToken
) {}
