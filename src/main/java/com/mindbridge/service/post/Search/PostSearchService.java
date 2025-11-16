package com.mindbridge.service.post.Search;

import com.mindbridge.dto.ResponseDto.PostListResponseDto;
import com.mindbridge.dto.ResponseDto.PostSliceResponseDto;
import com.mindbridge.entity.enums.Category;

public interface PostSearchService {
    PostSliceResponseDto<PostListResponseDto> searchPosts(Category category, String keyword, int page, int size);

}
