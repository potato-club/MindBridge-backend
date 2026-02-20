package com.mindbridge.dto.RequestDto.post;

import com.mindbridge.entity.enums.Category;

public record PostCreateRequestDto(
        Category category,
        String title,
        String content,
        Boolean isAnonymous
) {
}