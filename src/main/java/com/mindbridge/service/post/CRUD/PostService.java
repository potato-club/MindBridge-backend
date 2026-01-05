package com.mindbridge.service.post.CRUD;

import com.mindbridge.dto.RequestDto.PostCreateRequestDto;
import com.mindbridge.dto.ResponseDto.PostListResponseDto;
import com.mindbridge.dto.ResponseDto.PostResponseDto;
import com.mindbridge.dto.RequestDto.PostUpdateRequestDto;
import com.mindbridge.dto.ResponseDto.PageResponseDto;
import com.mindbridge.entity.enums.Category;

public interface PostService {
    PostResponseDto createPost(long userId, PostCreateRequestDto requestDTO);
    PageResponseDto<PostListResponseDto> getAllPosts(Category category, int page, int size);
    PostResponseDto getPost(Long id);
    PostResponseDto updatePost(long postId, PostUpdateRequestDto postUpdateRequestDto);
    void deletePost(long postId, long userId);
}