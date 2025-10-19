package com.mindbridge.dto.RequestDto;

import com.mindbridge.entity.UserEntity;
import com.mindbridge.entity.enums.Category;

public record PostCreateRequestDto(
        UserEntity userId,
        Category category,
        String title,
        String content,
        Boolean isAnonymous
) {
}
