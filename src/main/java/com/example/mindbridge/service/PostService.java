package com.example.mindbridge.service;

import com.example.mindbridge.dto.PostCreateRequestDTO;
import com.example.mindbridge.dto.PostResponseDTO;
import com.example.mindbridge.dto.PostUpdateRequestDTO;

import java.util.List;

public interface PostService {
    PostResponseDTO createPost(PostCreateRequestDTO requestDTO);
    List<PostResponseDTO> getAllPosts();
    PostResponseDTO getPost(Long id);
    PostResponseDTO updatePost(Long id, PostUpdateRequestDTO requestDTO);
    void deletePost(Long id);
}