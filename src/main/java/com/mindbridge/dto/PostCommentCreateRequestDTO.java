package com.mindbridge.dto;

import lombok.Data;

@Data
public class PostCommentCreateRequestDTO {
    private Long userId;
    private Long postId;
    private String content;
    private Boolean isAnonymous;
}
