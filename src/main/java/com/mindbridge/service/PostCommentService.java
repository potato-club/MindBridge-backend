package com.mindbridge.service;

import com.mindbridge.dto.RequestDto.PostCommentCreateRequestDto;
import com.mindbridge.dto.ResponseDto.PostCommentResponseDto;
import com.mindbridge.dto.RequestDto.PostCommentUpdateRequestDto;

import java.util.List;

public interface PostCommentService {
    PostCommentResponseDto createComment(Long userId, PostCommentCreateRequestDto postCommentCreateRequestDto);
    PostCommentResponseDto updateComment(Long id, PostCommentUpdateRequestDto dto);
    void deleteComment(Long id);
    boolean likeComment(Long commentId, Long userId);
    boolean unlikeComment(Long commentId, Long userId);
    List<PostCommentResponseDto> getCommentsByPost(Long postId);
}
