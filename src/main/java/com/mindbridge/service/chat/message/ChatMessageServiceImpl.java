package com.mindbridge.service.chat.message;

import com.mindbridge.dto.ChatMessage;
import com.mindbridge.dto.RequestDto.chat.ChatMessageRequestDto;
import com.mindbridge.dto.ResponseDto.chat.ChatMessageResponseDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomMessageResponseDto;
import com.mindbridge.entity.ChatMessageEntity;
import com.mindbridge.entity.ChatRoomEntity;
import com.mindbridge.entity.user.UserEntity;
import com.mindbridge.entity.enums.ChatMessageType;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.UserNotFoundException;
import com.mindbridge.repository.ChatMessageRepository;
import com.mindbridge.repository.ChatRoomMemberRepository;
import com.mindbridge.repository.ChatRoomRepository;
import com.mindbridge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService{
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final SimpMessageSendingOperations messageSendingOperations;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public void sendMessage(ChatMessageRequestDto message, long userId){

        validateMember(message.roomId(), userId);

        ChatRoomEntity chatRoom = chatRoomRepository.findById(message.roomId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        if (chatRoom.getCreatedAt().plusDays(1).isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("만료된 채팅방입니다.");
        }

        ChatMessageResponseDto responseDto = save(message, userId);
        Long roomId = responseDto.roomId();

        messageSendingOperations.convertAndSend(
                "/sub/chat/room/" + roomId,
                responseDto
        );

        List<Long> roomMemberIds =
                chatRoomMemberRepository.findAllUserIdsByChatRoomId(roomId);

        for (Long memberId : roomMemberIds) {
            messageSendingOperations.convertAndSend(
                    "/sub/chat/room/list/" + memberId,
                    responseDto
            );
        }
    }

    @Override
    public ChatRoomMessageResponseDto getChatRoomMessage(Long chatRoomId){
        List<ChatMessage> chatMessageList = chatMessageRepository.findChatMessagesByChatRoomId(chatRoomId);
        return new ChatRoomMessageResponseDto(chatMessageList);
    }

    private ChatMessageResponseDto save(ChatMessageRequestDto message, long userId) {

        ChatRoomEntity chatRoom = chatRoomRepository.findById(message.roomId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        // 메시지 db에 저장
        ChatMessageEntity chatMessageEntity = chatMessageRepository.save(
                ChatMessageEntity.builder()
                        .chatRoomId(chatRoom)
                        .content(message.message())
                        .messageType(ChatMessageType.TALK)
                        .senderId(user)
                        .build());

        return new ChatMessageResponseDto(
                chatMessageEntity.getChatRoomId().getId(),
                userId,
                user.getUsername(),
                chatMessageEntity.getContent()
        );
    }

    private void validateMember(Long roomId, Long userId) {

        ChatRoomEntity room = chatRoomRepository.getReferenceById(roomId);
        UserEntity user = userRepository.getReferenceById(userId);

        boolean exists = chatRoomMemberRepository
                .existsByChatRoomIdAndUserId(room, user);

        if (!exists) {
            throw new IllegalStateException("채팅방 멤버가 아닙니다.");
        }
    }
}
