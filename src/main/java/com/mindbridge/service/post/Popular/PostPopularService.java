package com.mindbridge.service.post.Popular;

import com.mindbridge.dto.ResponseDto.PostPopularResponseDto;

import java.util.List;

public interface PostPopularService {
    List<PostPopularResponseDto> getWeeklyTopPosts();

}
