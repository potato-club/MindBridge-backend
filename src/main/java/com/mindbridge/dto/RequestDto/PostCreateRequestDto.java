package com.mindbridge.dto.RequestDto;

import com.mindbridge.entity.enums.Category;

public record PostCreateRequestDto(
        Long userId,
        Category category,
        String title,
        String content,
        Boolean isAnonymous
) {
}
