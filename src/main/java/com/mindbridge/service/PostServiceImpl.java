package com.mindbridge.service;

import com.mindbridge.dto.RequestDTO.PostCreateRequestDTO;
import com.mindbridge.dto.ResponseDTO.PostResponseDTO;
import com.mindbridge.dto.RequestDTO.PostUpdateRequestDTO;
import com.mindbridge.entity.PostEntity;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.PostNotFoundException;
import com.mindbridge.mapper.PostMapper;
import com.mindbridge.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public PostResponseDTO createPost(PostCreateRequestDTO requestDTO) {
        PostEntity post = PostMapper.toEntity(requestDTO);
        PostEntity saved  = postRepository.save(post);
        return PostMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public PostResponseDTO getPost(Long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        postEntity.increaseViewCount();
        return PostMapper.toDTO(postEntity);
    }

    @Override
    @Transactional
    public PostResponseDTO updatePost(Long id, PostUpdateRequestDTO postUpdateRequestDTO) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        postEntity.update(
                postUpdateRequestDTO.title(),
                postUpdateRequestDTO.content()
        );
        return PostMapper.toDTO(postEntity);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}
