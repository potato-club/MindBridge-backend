package com.mindbridge.dto;

public record MyChatRoom(
        Long roomId,
        String roomName,
        String lastMessage,
        String imgUrl
) {}
