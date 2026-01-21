package com.mindbridge.dto.RequestDto.chat;

import java.util.List;

public record ChatRoomCreateRequestDto(
        String name,
        List<Long> memberIds
) {
}
