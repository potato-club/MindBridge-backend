package com.mindbridge.mapper;

import com.mindbridge.dto.RequestDTO.PostCreateRequestDTO;
import com.mindbridge.dto.ResponseDTO.PostResponseDTO;
import com.mindbridge.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public static PostEntity toEntity(PostCreateRequestDTO requestDTO) {
        return PostEntity.builder()
                .userId(requestDTO.userId())
                .category(requestDTO.category())
                .title(requestDTO.title())
                .content(requestDTO.content())
                .isAnonymous(requestDTO.isAnonymous())
                .viewCount(0)
                .likeCount(0)
                .commentCount(0)
                .build();
    }

    public static PostResponseDTO toDTO(PostEntity post) {
        return new PostResponseDTO(
                post.getId(),
                post.getUserId(),
                post.getCategory(),
                post.getTitle(),
                post.getContent(),
                post.getIsAnonymous(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getCommentCount()
        );
    }
}
