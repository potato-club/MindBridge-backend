package com.mindbridge.dto.RequestDto;

public record VerificationRequestDto(
        String phone,
        String code
) {}
