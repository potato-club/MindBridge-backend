package com.mindbridge.dto.RequestDto.chat;

public record ChatMessageRequestDto(
        long roomId,
        String message
) {}
