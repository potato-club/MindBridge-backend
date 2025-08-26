package com.mindbridge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
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
