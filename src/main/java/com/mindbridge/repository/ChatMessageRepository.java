package com.mindbridge.repository;

import com.mindbridge.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    void deleteByChatRoomId(Long chatRoomId);
}
