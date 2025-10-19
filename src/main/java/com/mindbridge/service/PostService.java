package com.mindbridge.service;

import com.mindbridge.dto.RequestDto.PostCreateRequestDto;
import com.mindbridge.dto.ResponseDto.PostListResponseDto;
import com.mindbridge.dto.ResponseDto.PostResponseDto;
import com.mindbridge.dto.RequestDto.PostUpdateRequestDto;
import com.mindbridge.dto.ResponseDto.PostSliceResponseDto;
import com.mindbridge.entity.enums.Category;

import java.util.List;

public interface PostService {
    PostResponseDto createPost(PostCreateRequestDto requestDTO);
    PostSliceResponseDto<PostListResponseDto> getAllPosts(Category category, int page, int size);
    PostResponseDto getPost(Long id);
    PostSliceResponseDto<PostListResponseDto> searchPosts(Category category, String keyword, int page, int size);
    PostResponseDto updatePost(Long id, PostUpdateRequestDto requestDTO);
    void deletePost(Long id);
    boolean toggleLike(Long postId, Long userId);
    List<PostResponseDto> getLikedPosts(Long userId);
    List<PostResponseDto> getWeeklyTopPosts();
}