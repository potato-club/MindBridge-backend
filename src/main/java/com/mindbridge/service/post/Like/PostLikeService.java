package com.mindbridge.service.post.Like;

import com.mindbridge.dto.ResponseDto.post.PostResponseDto;

import java.util.List;

public interface PostLikeService {
    boolean toggleLike(Long postId, Long userId);
    List<PostResponseDto> getLikedPosts(Long userId);

}
