package com.mindbridge.service;

import com.mindbridge.dto.RequestDto.PostCreateRequestDto;
import com.mindbridge.dto.ResponseDto.PostResponseDto;
import com.mindbridge.dto.RequestDto.PostUpdateRequestDto;

import java.util.List;

public interface PostService {
    PostResponseDto createPost(PostCreateRequestDto requestDTO);
    List<PostResponseDto> getAllPosts();
    PostResponseDto getPost(Long id);
    PostResponseDto updatePost(Long id, PostUpdateRequestDto requestDTO);
    void deletePost(Long id);
    boolean toggleLike(Long postId, Long userId);
    List<PostResponseDto> getLikedPosts(Long userId);
}