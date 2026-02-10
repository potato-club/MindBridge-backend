package com.mindbridge.dto.ResponseDto.chat;

import com.mindbridge.dto.MyChatRoom;

import java.util.List;

public record ChatRoomListResponseDto(
        List<MyChatRoom> chatRoomList
) {}
