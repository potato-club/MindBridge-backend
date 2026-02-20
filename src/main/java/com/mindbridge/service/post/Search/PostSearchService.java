package com.mindbridge.service.post.Search;

import com.mindbridge.dto.ResponseDto.post.PostListResponseDto;
import com.mindbridge.dto.ResponseDto.PageResponseDto;
import com.mindbridge.entity.enums.Category;

public interface PostSearchService {
    PageResponseDto<PostListResponseDto> searchPosts(Category category, String keyword, int page, int size);

}
