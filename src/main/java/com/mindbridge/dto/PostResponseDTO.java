package com.mindbridge.dto;

import com.mindbridge.entity.enums.Category;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class PostResponseDTO {
    private Long id;
    private Long userId;
    private Category category;
    private String title;
    private String content;
    private Boolean isAnonymous;
    private int viewCount;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
