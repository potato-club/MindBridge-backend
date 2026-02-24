package com.mindbridge.scheduler;

import com.mindbridge.service.chat.room.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRoomCleanupScheduler {

    private final ChatRoomService chatRoomService;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void cleanupExpiredChatRooms() {
        chatRoomService.deleteExpiredChatRooms();
    }

}
