package com.mindbridge.dto.RequestDto;

public record VerificationRequestDto(
        String phoneNumber,
        String code
) {}
