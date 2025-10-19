package com.mindbridge.mapper;

import com.mindbridge.dto.RequestDto.PostCreateRequestDto;
import com.mindbridge.dto.ResponseDto.PostResponseDto;
import com.mindbridge.entity.PostEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {
    public PostEntity toEntity(PostCreateRequestDto requestDTO) {
        return PostEntity.builder()
                .user(requestDTO.userId())
                .category(requestDTO.category())
                .title(requestDTO.title())
                .contents(requestDTO.content())
                .isAnonymous(requestDTO.isAnonymous())
                .viewCount(0)
                .likeCount(0)
                .commentCount(0)
                .build();
    }

    public PostResponseDto toDTO(PostEntity post) {
        return new PostResponseDto(
                post.getId(),
                post.getUser().getUsername(),
                post.getUser().getNickname(),
                post.getTitle(),
                post.getContents(),
                post.getIsAnonymous(),
                post.getCreatedAt(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getCommentCount()
        );
    }

    public List<PostResponseDto> toDTOList(List<PostEntity> posts) {
        return posts.stream()
                .map(this::toDTO)
                .toList();
    }

}
