package com.mindbridge.controller.chat;

import com.mindbridge.dto.RequestDto.chat.ChatMessageRequestDto;
import com.mindbridge.service.chat.message.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
}
