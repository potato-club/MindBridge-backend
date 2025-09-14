package com.mindbridge.service;

import com.mindbridge.dto.RequestDTO.PostCreateRequestDTO;
import com.mindbridge.dto.ResponseDTO.PostResponseDTO;
import com.mindbridge.dto.RequestDTO.PostUpdateRequestDTO;
import com.mindbridge.entity.PostEntity;
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
    public PostResponseDTO createPost(PostCreateRequestDTO requestDTO) {
        PostEntity post = PostEntity.builder()
                .userId(requestDTO.getUserId())
                .category(requestDTO.getCategory())
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .isAnonymous(requestDTO.getIsAnonymous())
                .viewCount(0)
                .likeCount(0)
                .commentCount(0)
                .build();

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
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        postEntity.increaseViewCount();
        PostEntity updatedPost = postRepository.save(postEntity);
        return PostMapper.toDTO(updatedPost);
    }

    @Override
    @Transactional
    public PostResponseDTO updatePost(Long id, PostUpdateRequestDTO postUpdateRequestDTO) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        postEntity.update(
                postUpdateRequestDTO.getTitle(),
                postUpdateRequestDTO.getContent()

        );
        return PostMapper.toDTO(postEntity);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}
