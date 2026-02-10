package com.mindbridge.dto;

import java.time.LocalDateTime;

public record ChatRoom(
        Long roomId,
        String category,
        String roomName,
        String contents,
        Long  userId,
        String userName,
        String imgUrl,
        LocalDateTime time
) {}
