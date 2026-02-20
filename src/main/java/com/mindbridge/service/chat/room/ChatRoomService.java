package com.mindbridge.service.chat.room;

import com.mindbridge.dto.RequestDto.chat.ChatRoomCreateRequestDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomCreateResponseDto;
import com.mindbridge.dto.ResponseDto.chat.ChatMyRoomListResponseDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomListResponseDto;
import com.mindbridge.entity.enums.Category;

import java.util.List;

public interface ChatRoomService {
    ChatRoomCreateResponseDto createChatRoom(Long userId, ChatRoomCreateRequestDto chatRoomCreateRequestDto);

    ChatMyRoomListResponseDto getMyChatRoomList(Long userId);

    List<ChatRoomListResponseDto> getChatRoomList(Category category);

    void deleteExpiredChatRooms();
}
