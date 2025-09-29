package com.mindbridge.mapper;

import com.mindbridge.dto.ResponseDto.PostCommentResponseDto;
import com.mindbridge.entity.CommentEntity;
import com.mindbridge.service.PostRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostCommentMapper {

    @Autowired
    private PostRedisService postRedisService;

    public PostCommentResponseDto toDto(CommentEntity comment) {
        int likeCount = postRedisService.getLikeCount("comment:" + comment.getId());

        return new PostCommentResponseDto(
                comment.getId(),
                comment.getUserId(),
                comment.getPostId(),
                comment.getParentId(),
                comment.getContent(),
                comment.getIsAnonymous(),
                likeCount,
                comment.getCreatedAt()
        );
    }

    public List<PostCommentResponseDto> toDtoList(List<CommentEntity> comments) {
        return comments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}