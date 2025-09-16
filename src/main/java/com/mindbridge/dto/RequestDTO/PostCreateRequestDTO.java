package com.mindbridge.dto.RequestDTO;

import com.mindbridge.entity.enums.Category;

public record PostCreateRequestDTO (
        Long userId,
        Category category,
        String title,
        String content,
        Boolean isAnonymous
) {
}
