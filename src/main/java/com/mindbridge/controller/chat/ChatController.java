package com.mindbridge.controller.chat;

import com.mindbridge.dto.RequestDto.chat.ChatRoomCreateRequestDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomCreateResponseDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomListResponseDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomMessageResponseDto;
import com.mindbridge.jwt.CustomUserDetails;
import com.mindbridge.service.chat.message.ChatMessageService;
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
    private final ChatMessageService chatMessageService;

    @PostMapping("/room")
    @Operation(summary = "채팅방 생성 by 조민기")
    public ResponseEntity<ChatRoomCreateResponseDto> createChatRoom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ChatRoomCreateRequestDto chatRoomCreateRequestDto
    ) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(chatRoomService.createChatRoom(userId, chatRoomCreateRequestDto));
    }

    @GetMapping("/room/list")
    @Operation(summary = "사용자의 채팅방 list 조회 by 조민기")
    public ResponseEntity<ChatRoomListResponseDto> getChatRoomList(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(chatRoomService.getChatRoomList(userDetails.getId()));
    }


    @GetMapping("/message/{chatRoomId}")
    @Operation(summary = "채팅방 메시지 조회 by 조민기")
    public ResponseEntity<ChatRoomMessageResponseDto> getChatMessage(
            @PathVariable Long chatRoomId) {

        return ResponseEntity.ok(chatMessageService.getChatRoomMessage(chatRoomId));
    }
}
