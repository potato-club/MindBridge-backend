package com.mindbridge.entity;

import com.mindbridge.entity.enums.Category;
import com.mindbridge.entity.user.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_rooms")
public class ChatRoomEntity extends BaseEntity {
    private String roomName;

    @Enumerated(EnumType.STRING)
    private Category category;
}