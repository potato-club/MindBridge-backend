package com.mindbridge.repository;

import com.mindbridge.entity.ChatRoomMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMemberEntity, Long> {

    void deleteByChatRoomId(Long chatRoomId);

}
