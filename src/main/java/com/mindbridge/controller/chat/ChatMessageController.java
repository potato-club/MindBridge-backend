package com.mindbridge.controller.chat;

import com.mindbridge.dto.RequestDto.chat.ChatMessageRequestDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomMessageResponseDto;
import com.mindbridge.service.chat.message.ChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @MessageMapping("/message")
    public void message(
            ChatMessageRequestDto message,
            Principal principal
    ){
        long userId = Long.parseLong(principal.getName());

        chatMessageService.sendMessage(message,userId);
    }

    @GetMapping("/message/{chatRoomId}")
    @Operation(summary = "채팅방 메시지 조회 by 조민기")
    public ResponseEntity<ChatRoomMessageResponseDto> getChatMessage(
            @PathVariable Long chatRoomId) {

        return ResponseEntity.ok(chatMessageService.getChatRoomMessage(chatRoomId));
    }
}
