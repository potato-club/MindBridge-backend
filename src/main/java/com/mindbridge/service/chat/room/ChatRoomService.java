package com.mindbridge.service.chat.room;

import com.mindbridge.dto.RequestDto.chat.ChatRoomCreateRequestDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomCreateResponseDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomListResponseDto;

public interface ChatRoomService {
    ChatRoomCreateResponseDto createChatRoom(Long userId, ChatRoomCreateRequestDto chatRoomCreateRequestDto);

    ChatRoomListResponseDto getMyChatRoomList(Long userId);

    ChatRoomListResponseDto getChatRoomList(String category);

    void deleteExpiredChatRooms();
}
