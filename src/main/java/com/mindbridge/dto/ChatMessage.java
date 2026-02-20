package com.mindbridge.dto;

import java.time.LocalDateTime;

public record ChatMessage(
        long senderId,
        String senderName,
        String message,
        String senderProfileUrl,
        LocalDateTime sendTime
) {}
