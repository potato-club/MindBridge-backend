package com.mindbridge.service;

import com.mindbridge.dto.RequestDTO.PostCreateRequestDTO;
import com.mindbridge.dto.ResponseDTO.PostResponseDTO;
import com.mindbridge.dto.RequestDTO.PostUpdateRequestDTO;

import java.util.List;

public interface PostService {
    PostResponseDTO createPost(PostCreateRequestDTO requestDTO);
    List<PostResponseDTO> getAllPosts();
    PostResponseDTO getPost(Long id);
    PostResponseDTO updatePost(Long id, PostUpdateRequestDTO requestDTO);
    void deletePost(Long id);
}