package com.mindbridge.mapper;

import com.mindbridge.dto.ResponseDTO.PostResponseDTO;
import com.mindbridge.entity.PostEntity;

public class PostMapper {
    public static PostResponseDTO toDTO(PostEntity post) {
        return PostResponseDTO.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .category(post.getCategory())
                .title(post.getTitle())
                .content(post.getContent())
                .isAnonymous(post.getIsAnonymous())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
