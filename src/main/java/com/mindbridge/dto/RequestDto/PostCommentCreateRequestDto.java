package com.mindbridge.dto.RequestDto;

public record PostCommentCreateRequestDto(
        Long userId,
        Long parentId,
        String content,
        Boolean isAnonymous
) {}
