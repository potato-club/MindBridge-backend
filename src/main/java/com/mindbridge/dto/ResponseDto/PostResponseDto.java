package com.mindbridge.dto.ResponseDto;

import com.mindbridge.entity.enums.Category;

public record PostResponseDto(
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
