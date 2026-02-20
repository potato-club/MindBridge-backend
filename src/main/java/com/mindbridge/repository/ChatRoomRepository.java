package com.mindbridge.repository;

import com.mindbridge.dto.MyChatRoom;
import com.mindbridge.dto.ResponseDto.chat.ChatRoomListResponseDto;
import com.mindbridge.entity.ChatRoomEntity;
import com.mindbridge.entity.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    @Query("""
    SELECT new com.mindbridge.dto.MyChatRoom(
        cr.id,
        cr.roomName,
        cm.content,
        u.profileImage
    )
    FROM ChatRoomMemberEntity crm
    JOIN ChatRoomEntity cr
    JOIN ChatRoomMemberEntity opponent
        ON opponent.chatRoomId = cr
       AND opponent.userId.id <> :userId
    JOIN opponent.userId u
    LEFT JOIN ChatMessageEntity cm
        ON cm.chatRoomId = cr
       AND cm.createdAt = (
           SELECT MAX(cm2.createdAt)
           FROM ChatMessageEntity cm2
           WHERE cm2.chatRoomId = cr
       )
    WHERE crm.userId = :userId
      AND cr.createdAt > :expireTime
    ORDER BY cm.createdAt DESC
""")
    List<MyChatRoom> getMyChatRoomList(@Param("userId") Long userId);

    @Query("""
    SELECT new com.mindbridge.dto.ResponseDto.chat.ChatRoomListResponseDto(
        cr.id,
        cr.category,
        cr.roomName,
        cm.content,
        u.id,
        u.username,
        u.profileImage,
        cr.createdAt
    )
    FROM ChatRoomEntity cr
    LEFT JOIN ChatMessageEntity cm
    ON cm.chatRoomId = cr
    LEFT JOIN cm.senderId u
    WHERE (:category IS NULL OR cr.category = :category)
    AND cr.createdAt >= :threshold
    AND cm.createdAt = (
        SELECT MAX(cm2.createdAt)
        FROM ChatMessageEntity cm2
        WHERE cm2.chatRoomId = cr
    )
    ORDER BY cr.createdAt DESC
""")
    List<ChatRoomListResponseDto> findActiveChatRoomListByCategory(
            @Param("category") Category category,
            @Param("threshold") LocalDateTime threshold);

    @Modifying
    @Query("""
        DELETE FROM ChatRoomEntity cr
        WHERE cr.createdAt < :expireTime
        """)
    void deleteExpiredRooms(@Param("expireTime") LocalDateTime expireTime);
}
