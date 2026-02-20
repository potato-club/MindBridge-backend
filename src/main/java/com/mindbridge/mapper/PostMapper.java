package com.mindbridge.mapper;

import com.mindbridge.dto.RequestDto.post.PostCreateRequestDto;
import com.mindbridge.dto.ResponseDto.post.PostPopularResponseDto;
import com.mindbridge.dto.ResponseDto.post.PostResponseDto;
import com.mindbridge.entity.PostEntity;
import com.mindbridge.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {
    public PostEntity toEntity(UserEntity user, PostCreateRequestDto requestDto) {

        return PostEntity.builder()
                .user(user)
                .category(requestDto.category())
                .title(requestDto.title())
                .contents(requestDto.content())
                .isAnonymous(requestDto.isAnonymous())
                .viewCount(0)
                .likeCount(0)
                .commentCount(0)
                .build();
    }

    public PostResponseDto toDTO(PostEntity post) {
        return new PostResponseDto(
                post.getId(),
                post.getCategory(),
                post.getUser().getUsername(),
                post.getUser().getProfileImage(),
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

    public PostPopularResponseDto toPopularDto(PostEntity post) {
        return new PostPopularResponseDto(
                post.getId(),
                post.getTitle(),
                post.getUser().getNickname(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getCommentCount()
        );
    }

    public List<PostPopularResponseDto> toPopularDtoList(List<PostEntity> posts) {
        return posts.stream()
                .map(this::toPopularDto)
                .toList();
    }
}