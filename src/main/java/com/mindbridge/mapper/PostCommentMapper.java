package com.mindbridge.mapper;

import com.mindbridge.dto.ResponseDTO.PostCommentResponseDTO;
import com.mindbridge.entity.CommentEntity;

public class PostCommentMapper {
    public static PostCommentResponseDTO commentToDTO(CommentEntity commentEntity) {
        return PostCommentResponseDTO.builder()
                .id(commentEntity.getId())
                .userId(commentEntity.getUserId())
                .postId(commentEntity.getPostId() != null ? commentEntity.getPostId().getId() : null)
                .parent(commentEntity.getParent() != null ? commentEntity.getParent().getId() : null)
                .content(commentEntity.getContent())
                .isAnonymous(commentEntity.getIsAnonymous())
                .build();
    }

}