package com.mindbridge.dto.RequestDto.chat;

import com.mindbridge.entity.enums.Category;

import java.util.List;

public record ChatRoomCreateRequestDto(
        String name,
        Category category,
        List<Long> memberIds
) {}
