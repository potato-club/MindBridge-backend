package com.mindbridge.dto.ResponseDto;

import com.mindbridge.entity.enums.Category;

import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        Category category,
        String writerName,
        String profileUrl,
        String nickname,
        String title,
        String contents,
        Boolean isAnonymous,
        LocalDateTime createdAt,
        int viewCount,
        int likeCount,
        int commentCount
) {}