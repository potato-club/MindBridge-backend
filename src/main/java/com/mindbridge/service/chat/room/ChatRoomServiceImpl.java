package com.mindbridge.service.chat.room;

import com.mindbridge.dto.MyChatRoom;
import com.mindbridge.dto.RequestDto.chat.ChatRoomCreateRequestDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomCreateResponseDto;
import com.mindbridge.dto.ResponseDto.chat.ChatMyRoomListResponseDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomListResponseDto;
import com.mindbridge.entity.ChatRoomEntity;
import com.mindbridge.entity.ChatRoomMemberEntity;
import com.mindbridge.entity.user.UserEntity;
import com.mindbridge.entity.enums.Category;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.CustomException;
import com.mindbridge.repository.ChatMessageRepository;
import com.mindbridge.repository.ChatRoomMemberRepository;
import com.mindbridge.repository.ChatRoomRepository;
import com.mindbridge.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ChatRoomCreateResponseDto createChatRoom(Long userId, ChatRoomCreateRequestDto chatRoomCreateRequestDto) {

        UserEntity creator = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //채팅방 생성
        ChatRoomEntity chatRoom = chatRoomRepository.save(
                ChatRoomEntity.builder()
                        .roomName(chatRoomCreateRequestDto.name())
                        .category(chatRoomCreateRequestDto.category())
                        .build()
        );

        //생성자도 멤버로 추가
        chatRoomMemberRepository.save(
                ChatRoomMemberEntity.builder()
                        .chatRoomId(chatRoom)
                        .userId(creator)
                        .unreadCount(0)
                        .build()
        );

        if (chatRoomCreateRequestDto.memberIds() != null) {
            for (Long memberId : chatRoomCreateRequestDto.memberIds()) {

                //본인 중복 방지
                if (memberId.equals(userId)) {
                    continue;
                }

                UserEntity member = userRepository.findById(memberId)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

                chatRoomMemberRepository.save(
                        ChatRoomMemberEntity.builder()
                                .chatRoomId(chatRoom)
                                .userId(member)
                                .unreadCount(0)
                                .build()
                );
            }
        }

        return new ChatRoomCreateResponseDto(chatRoom.getId());
    }

    @Override
    @Transactional
    public ChatMyRoomListResponseDto getMyChatRoomList(Long userId) {// 내가 진행중인 채팅
        List<MyChatRoom> chatRooms = chatRoomRepository.getMyChatRoomList(userId);

        return new ChatMyRoomListResponseDto(chatRooms);
    }

    @Override
    @Transactional
    public List<ChatRoomListResponseDto> getChatRoomList(Category category) {

        LocalDateTime threshold = LocalDateTime.now().minusDays(1);

        return chatRoomRepository.findActiveChatRoomListByCategory(category, threshold);
    }

    @Override
    @Transactional
    public void deleteExpiredChatRooms() {
        LocalDateTime expireTime = LocalDateTime.now().minusHours(24);

        chatMessageRepository.deleteExpiredMessages(expireTime);
        chatRoomMemberRepository.deleteExpiredMembers(expireTime);
        chatRoomRepository.deleteExpiredRooms(expireTime);

    }
}