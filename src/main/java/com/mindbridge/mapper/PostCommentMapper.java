package com.mindbridge.mapper;

import com.mindbridge.dto.PostCommentResponseDTO;
import com.mindbridge.entity.CommentEntity;

public class PostCommentMapper {
    public static PostCommentResponseDTO commentToDTO(CommentEntity commentEntity) {
        return PostCommentResponseDTO.builder()
                .id(commentEntity.getId())
                .userId(commentEntity.getUserId())
                .postId(commentEntity.getPostId())
                .parent(commentEntity.getParent())
                .replies(commentEntity.getReplies())
                .content(commentEntity.getContent())
                .isAnonymous(commentEntity.getIsAnonymous())
                .build();
    }

}