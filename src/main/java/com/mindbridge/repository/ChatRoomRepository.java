package com.mindbridge.repository;

import com.mindbridge.dto.ChatRoom;
import com.mindbridge.entity.ChatRoomEntity;
import com.mindbridge.entity.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    @Query("""
    SELECT new com.mindbridge.dto.ChatRoom(
        cr.id,
        cr.roomName,
        cm.content,
        u.profileImage
    )
    FROM ChatRoomMemberEntity crm
    JOIN ChatRoomEntity cr ON crm.chatRoomId = cr.id
    JOIN ChatRoomMemberEntity opponent
        ON opponent.chatRoomId = cr.id
       AND opponent.userId <> :userId
    JOIN UserEntity u ON opponent.userId = u.id
    LEFT JOIN ChatMessageEntity cm
        ON cm.chatRoomId = cr.id
       AND cm.createdAt = (
           SELECT MAX(cm2.createdAt)
           FROM ChatMessageEntity cm2
           WHERE cm2.chatRoomId = cr.id
       )
    WHERE crm.userId = :userId
      AND cr.expiredAt > CURRENT_TIMESTAMP
    ORDER BY cm.createdAt DESC
""")
    List<ChatRoom> getMyChatRoomList(@Param("userId") Long userId);

    List<ChatRoomEntity> findByActiveTrueOrderByCreatedAtDesc();

    List<ChatRoomEntity> findByTopicAndActiveTrueOrderByCreatedAtDesc(Category category);

    List<ChatRoomEntity> findByExpiredAtBefore(LocalDateTime time);
}
