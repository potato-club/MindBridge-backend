package com.mindbridge.service;

import com.mindbridge.dto.PostCommentCreateRequestDTO;
import com.mindbridge.dto.PostCommentResponseDTO;
import com.mindbridge.dto.PostCommentUpdateRequestDTO;

import java.util.List;

public interface PostCommentService {
    PostCommentResponseDTO createComment(PostCommentCreateRequestDTO commentCreateRequestDTO);
    List<PostCommentResponseDTO> getCommentsByPost(Long postId);
    List<PostCommentResponseDTO> getCommentsWithRepliesByPost(Long postId);
    PostCommentResponseDTO updateComment(Long id, PostCommentUpdateRequestDTO commentUpdateRequestDTO);
    void deleteComment(Long id);
}
