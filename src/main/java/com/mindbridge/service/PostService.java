package com.mindbridge.service;

import com.mindbridge.dto.PostCreateRequestDTO;
import com.mindbridge.dto.PostResponseDTO;
import com.mindbridge.dto.PostUpdateRequestDTO;

import java.util.List;

public interface PostService {
    PostResponseDTO createPost(PostCreateRequestDTO requestDTO);
    List<PostResponseDTO> getAllPosts();
    PostResponseDTO getPost(Long id);
    PostResponseDTO updatePost(Long id, PostUpdateRequestDTO requestDTO);
    void deletePost(Long id);
}