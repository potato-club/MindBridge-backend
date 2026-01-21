package com.mindbridge.dto;

public record ChatRoom(
        Long roomId,
        String roomName,
        String lastMessage,
        String imgUrl
) {
}
