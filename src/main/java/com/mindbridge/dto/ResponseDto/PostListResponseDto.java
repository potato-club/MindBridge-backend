package com.mindbridge.dto.ResponseDto;

import com.mindbridge.entity.enums.Category;

import java.time.LocalDateTime;

public record PostListResponseDto(
        long id,
        String writerName,
        String nickname,
        Category category,
        String title,
        String contents,
        Boolean isAnonymous,
        LocalDateTime createdAt,
        int viewCount,
        int likeCount,
        int CommentCount
) {}
