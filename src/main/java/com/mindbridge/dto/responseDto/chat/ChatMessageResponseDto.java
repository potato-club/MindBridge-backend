package com.mindbridge.dto.ResponseDto.chat;

public record ChatMessageResponseDto(
        Long roomId,
        Long senderId,
        String senderName,
        String message
) {}
