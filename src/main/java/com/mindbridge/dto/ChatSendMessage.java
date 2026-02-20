package com.mindbridge.dto;

public record ChatSendMessage(
        long roomId,
        long receiverId,
        String message
) {
}
