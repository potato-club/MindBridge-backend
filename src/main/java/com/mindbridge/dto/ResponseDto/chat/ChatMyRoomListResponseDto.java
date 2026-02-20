package com.mindbridge.dto.ResponseDto.chat;

import com.mindbridge.dto.MyChatRoom;

import java.util.List;

public record ChatMyRoomListResponseDto(
        List<MyChatRoom> chatRoomList
) {}
