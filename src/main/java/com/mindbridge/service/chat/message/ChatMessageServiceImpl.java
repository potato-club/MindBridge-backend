package com.mindbridge.service.chat.message;

import com.mindbridge.dto.ChatMessage;
import com.mindbridge.dto.RequestDto.chat.ChatMessageRequestDto;
import com.mindbridge.dto.ResponseDto.chat.ChatMessageResponseDto;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomMessageResponseDto;
import com.mindbridge.entity.ChatMessageEntity;
import com.mindbridge.entity.enums.ChatMessageType;
import com.mindbridge.repository.ChatMessageRepository;
import com.mindbridge.repository.ChatRoomMemberRepository;
import com.mindbridge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService{
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final SimpMessageSendingOperations messageSendingOperations;

    @Override
    public void sendMessage(ChatMessageRequestDto message, long userId){
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
        // 메시지 db에 저장
        ChatMessageEntity chatMessageEntity = chatMessageRepository.save(ChatMessageEntity.builder()
                .chatRoomId(message.roomId())
                .content(message.message())
                .messageType(ChatMessageType.TALK)
                .senderId(userId)
                .build());

        // 전송자 이름 get
        String senderName = userRepository.findNameById(userId);

        return new ChatMessageResponseDto(
                chatMessageEntity.getChatRoomId(),
                userId,
                senderName,
                chatMessageEntity.getContent()
        );
    }
}
