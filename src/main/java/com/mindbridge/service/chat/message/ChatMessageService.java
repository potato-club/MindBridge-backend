package com.mindbridge.service.chat.message;

import com.mindbridge.dto.RequestDto.chat.ChatMessageRequestDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomMessageResponseDto;

public interface ChatMessageService {
    void sendMessage(ChatMessageRequestDto message, long userId);

    ChatRoomMessageResponseDto getChatRoomMessage(Long chatRoomId);
}
