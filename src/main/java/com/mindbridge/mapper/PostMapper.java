package com.mindbridge.mapper;

import com.mindbridge.dto.RequestDto.PostCreateRequestDto;
import com.mindbridge.dto.ResponseDto.PostResponseDto;
import com.mindbridge.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public static PostEntity toEntity(PostCreateRequestDto requestDTO) {
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

    public static PostResponseDto toDTO(PostEntity post) {
        return new PostResponseDto(
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
