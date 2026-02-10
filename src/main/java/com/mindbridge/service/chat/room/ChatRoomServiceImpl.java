package com.mindbridge.service.chat.room;

import com.mindbridge.dto.ChatRoom;
import com.mindbridge.dto.RequestDto.chat.ChatRoomCreateRequestDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomCreateResponseDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomListResponseDto;
import com.mindbridge.entity.ChatRoomEntity;
import com.mindbridge.entity.ChatRoomMemberEntity;
import com.mindbridge.entity.enums.Category;
import com.mindbridge.repository.ChatMessageRepository;
import com.mindbridge.repository.ChatRoomMemberRepository;
import com.mindbridge.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    @Transactional
    public ChatRoomCreateResponseDto createChatRoom(Long userId, ChatRoomCreateRequestDto chatRoomCreateRequestDto) {
        LocalDateTime expiredAt = LocalDateTime.now().plusDays(1);

        ChatRoomEntity chatRoom = chatRoomRepository.save(
                ChatRoomEntity.builder()
                        .roomName(chatRoomCreateRequestDto.name())
                        .expiredAt(expiredAt)
                        .build()
        );

        for (Long memberId : chatRoomCreateRequestDto.memberIds()) {
            chatRoomMemberRepository.save(
                    ChatRoomMemberEntity.builder()
                            .chatRoomId(chatRoom.getId())
                            .userId(memberId)
                            .build()
            );
        }

        return new ChatRoomCreateResponseDto(chatRoom.getId());
    }

    @Override
    public ChatRoomListResponseDto getMyChatRoomList(Long userId) {// 내가 진행중인 채팅
        List<ChatRoom> chatRooms = chatRoomRepository.getMyChatRoomList(userId);

        return new ChatRoomListResponseDto(chatRooms);
    }

    @Override
    public ChatRoomListResponseDto getChatRoomList(String category) {
        List<ChatRoomEntity> rooms;

        if (category == null || category.equalsIgnoreCase("ALL")) {
            rooms = chatRoomRepository.findByActiveTrueOrderByCreatedAtDesc();
        } else {
            Category topic =  Category.valueOf(category);
            rooms = chatRoomRepository.findByTopicAndActiveTrueOrderByCreatedAtDesc(topic);
        }

        return rooms.stream()
                .map(room -> new ChatRoomListResponseDto(
                        room.getId(),
                        room.getRoomName(),
                        room.getCategory()
                )).toList();
    }//roomId, category, roomName, contents, userId, username(nickname), procfileImgUrl, time(남은시간)

    @Override
    @Transactional
    public void deleteExpiredChatRooms() {
        LocalDateTime now = LocalDateTime.now();

        List<ChatRoomEntity> expiredRooms =
                chatRoomRepository.findByExpiredAtBefore(now);

        for (ChatRoomEntity roomEntity : expiredRooms) {
            Long roomId = roomEntity.getId();

            chatMessageRepository.deleteByChatRoomId(roomId);
            chatRoomMemberRepository.deleteByChatRoomId(roomId);
            chatRoomRepository.delete(roomEntity);
        }
    }
}
