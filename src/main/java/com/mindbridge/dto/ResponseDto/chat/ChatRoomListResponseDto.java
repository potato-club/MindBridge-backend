package com.mindbridge.dto.ResponseDto.chat;

import com.mindbridge.entity.enums.Category;

import java.time.LocalDateTime;

public record ChatRoomListResponseDto(
        Long roomId,
        Category category,
        String roomName,
        String contents,
        Long userId,
        String username,
        String profileImgUrl,
        LocalDateTime expiredAt
) {}