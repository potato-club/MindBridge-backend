package com.mindbridge.dto.ResponseDto;

public record PostPopularResponseDto(
        long id,
        String title,
        String writerName,
        int viewCount,
        int likeCount,
        int commentCount
) {}
