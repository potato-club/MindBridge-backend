package com.mindbridge.mapper;

import com.mindbridge.dto.RequestDto.PostCreateRequestDto;
import com.mindbridge.dto.ResponseDto.PostCommentResponseDto;
import com.mindbridge.dto.ResponseDto.PostResponseDto;
import com.mindbridge.entity.PostEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

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

    public static PostResponseDto toDTO(PostEntity post, List<PostCommentResponseDto> comments) {
        return new PostResponseDto(
                post.getId(),
                post.getUserId(),
                post.getCategory(),
                post.getTitle(),
                post.getContent(),
                post.getIsAnonymous(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getCommentCount(),
                comments
        );
    }

    public static PostResponseDto toDTO(PostEntity post) {
        return toDTO(post, Collections.emptyList());
    }

    public static List<PostResponseDto> toDTOList(List<PostEntity> posts) {
        return posts.stream()
                .map(PostMapper::toDTO)
                .toList();
    }

}
