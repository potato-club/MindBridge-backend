package com.mindbridge.service.post.CRUD;

import com.mindbridge.dto.RequestDto.post.PostCreateRequestDto;
import com.mindbridge.dto.ResponseDto.post.PostListResponseDto;
import com.mindbridge.dto.ResponseDto.post.PostResponseDto;
import com.mindbridge.dto.RequestDto.post.PostUpdateRequestDto;
import com.mindbridge.dto.ResponseDto.PageResponseDto;
import com.mindbridge.entity.enums.Category;

public interface PostService {
    PostResponseDto createPost(long userId, PostCreateRequestDto requestDTO);
    PageResponseDto<PostListResponseDto> getAllPosts(Category category, int page, int size);
    PostResponseDto getPost(Long id);
    PostResponseDto updatePost(long postId, PostUpdateRequestDto postUpdateRequestDto);
    void deletePost(long postId, long userId);
}