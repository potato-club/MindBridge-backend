package com.mindbridge.dto.ResponseDTO;

import com.mindbridge.entity.enums.Category;

public record PostResponseDTO (
        Long id,
        Long userId,
        Category category,
        String title,
        String content,
        Boolean isAnonymous,
        int viewCount,
        int likeCount,
        int commentCount
) {}
