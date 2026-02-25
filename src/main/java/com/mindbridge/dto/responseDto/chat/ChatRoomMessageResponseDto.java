package com.mindbridge.dto.ResponseDto.chat;

import com.mindbridge.dto.ChatMessage;

import java.util.List;

public record ChatRoomMessageResponseDto(
        List<ChatMessage> messages
) {}
