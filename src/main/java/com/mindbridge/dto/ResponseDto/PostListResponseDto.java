package com.mindbridge.dto.ResponseDto;

import java.time.LocalDateTime;

public record PostListResponseDto(
        long id,
        String writerName,
        String nickname,
        String profileUrl,
        String title,
        String contents,
        Boolean isAnonymous,
        LocalDateTime createdAt,
        int viewCount,
        int likeCount,
        int CommentCount
) {}