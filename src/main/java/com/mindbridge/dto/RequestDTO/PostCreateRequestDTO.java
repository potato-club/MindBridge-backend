package com.mindbridge.dto.RequestDTO;

import com.mindbridge.entity.enums.Category;
import lombok.Data;

@Data
public class PostCreateRequestDTO {
    private Long userId;
    private Category category;
    private String title;
    private String content;
    private Boolean isAnonymous;
}
