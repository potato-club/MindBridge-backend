package com.mindbridge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequestDTO {
    private Long userId;
    private Long boardId;
    private Long categoryId;
    private String title;
    private String content;
    private Boolean isAnonymous;
}
