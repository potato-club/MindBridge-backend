package com.mindbridge.dto.ResponseDto.post;

public record PostPopularResponseDto(
        long id,
        String title,
        String writerName,
        int viewCount,
        int likeCount,
        int commentCount
) {}
