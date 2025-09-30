package com.mindbridge.dto.ResponseDto;

import java.time.LocalDateTime;

public record PostCommentResponseDto(
        Long id,
        Long userId,
        Long postId,
        Long parentId,
        String content,
        Boolean isAnonymous,
        int likeCount,
        LocalDateTime createdAt
) {}

