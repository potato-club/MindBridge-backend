package com.mindbridge.dto;

import com.mindbridge.entity.CommentEntity;
import com.mindbridge.entity.PostEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PostCommentResponseDTO {
    private Long id;
    private Long userId;
    private PostEntity postId;
    private CommentEntity parent;

    @Builder.Default
    private List<PostCommentResponseDTO> replies = new ArrayList<>();
    private String content;
    private Boolean isAnonymous;
    private int likeCount;
    private int disLikeCount;
    private LocalDateTime createdAt;
}
