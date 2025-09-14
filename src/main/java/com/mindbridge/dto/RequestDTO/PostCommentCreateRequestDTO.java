package com.mindbridge.dto.RequestDTO;

import lombok.Data;

@Data
public class PostCommentCreateRequestDTO {
    private Long userId;
    private Long postId;
    private Long parentId;
    private String content;
    private Boolean isAnonymous;
}
