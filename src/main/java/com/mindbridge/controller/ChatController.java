package com.mindbridge.controller;

import com.mindbridge.dto.RequestDto.chat.ChatRoomCreateRequestDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomCreateResponseDto;
import com.mindbridge.jwt.CustomUserDetails;
import com.mindbridge.service.chat.room.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/room")
    @Operation(summary = "채팅방 생성 by 조민기")
    public ResponseEntity<ChatRoomCreateResponseDto> createChatRoom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ChatRoomCreateRequestDto chatRoomCreateRequestDto
    ) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(chatRoomService.createChatRoom(userId, chatRoomCreateRequestDto));
    }

}
