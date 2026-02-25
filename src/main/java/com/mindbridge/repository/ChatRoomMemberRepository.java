package com.mindbridge.repository;

import com.mindbridge.entity.ChatRoomEntity;
import com.mindbridge.entity.ChatRoomMemberEntity;
import com.mindbridge.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMemberEntity, Long> {

    @Query("""
            SELECT crm.userId
            FROM ChatRoomMemberEntity crm
            WHERE crm.chatRoomId = :chatRoomId
            """)
    List<Long> findAllUserIdsByChatRoomId(@Param("chatRoomId")Long chatRoomId);

    boolean existsByChatRoomIdAndUserId(ChatRoomEntity chatRoom, UserEntity user);

    @Modifying
    @Query("""
        DELETE FROM ChatRoomMemberEntity crm
        WHERE crm.chatRoomId.createdAt < :expireRime
        """)
    void deleteExpiredMembers(@Param("expireTime") LocalDateTime expireTime);
}
