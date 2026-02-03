package com.mindbridge.dto.ResponseDto.chat;

import com.mindbridge.dto.ChatRoom;

import java.util.List;

public record ChatRoomListResponseDto(
        List<ChatRoom> chatRoomList
) {}
