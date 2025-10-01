package com.mindbridge.service;

import com.mindbridge.dto.ResponseDto.PostResponseDto;

import java.util.List;

public interface BookmarkService {
    void addBookmark(Long userId, Long postId);
    boolean isBookmarkde(Long userId, Long postId);
    void removeBookmark(Long userId, Long postId);
    List<PostResponseDto> getBookmarkedPosts(Long userId);
}
