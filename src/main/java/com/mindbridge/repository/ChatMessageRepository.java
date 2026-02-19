package com.mindbridge.repository;

import com.mindbridge.dto.ChatMessage;
import com.mindbridge.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    @Query("""
            SELECT new com.mindbridge.dto.ChatMessage(
                        u.id,
                        u.username,
                        cm.content,
                        u.profileImage,
                        cm.createdAt
            )
            FROM ChatMessageEntity cm
            JOIN cm.senderId u
            WHERE cm.chatRoomId.id = :chatRoomId
            ORDER BY cm.createdAt
            """)
    List<ChatMessage> findChatMessagesByChatRoomId(@Param("chatRoomId") Long chatRoomId);

    @Modifying
    @Query("""
        DELETE FROM ChatMessageEntity cm
        WHERE cm.chatRoomId.createdAt < :expireRime
        """)
    void deleteExpiredMessages(@Param("expireTime")LocalDateTime expireTime);
}
