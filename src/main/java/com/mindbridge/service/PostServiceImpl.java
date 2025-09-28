package com.mindbridge.service;

import com.mindbridge.dto.RequestDto.PostCreateRequestDto;
import com.mindbridge.dto.ResponseDto.PostResponseDto;
import com.mindbridge.dto.RequestDto.PostUpdateRequestDto;
import com.mindbridge.entity.PostEntity;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.PostNotFoundException;
import com.mindbridge.mapper.PostMapper;
import com.mindbridge.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostRedisService redisService;

    @Override
    @Transactional
    public PostResponseDto createPost(PostCreateRequestDto requestDTO) {
        PostEntity post = PostMapper.toEntity(requestDTO);
        PostEntity saved  = postRepository.save(post);
        return PostMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public PostResponseDto getPost(Long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        postEntity.increaseViewCount();
        return PostMapper.toDTO(postEntity);
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(Long id, PostUpdateRequestDto postUpdateRequestDTO) {
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

    @Override
    @Transactional
    public boolean toggleLike(Long postId, Long userId) {
        String postIdStr = String.valueOf(postId);
        String userIdStr = String.valueOf(userId);

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        if (redisService.hasLiked(postIdStr, userIdStr)) {
            // 좋아요 취소
            redisService.removeLike(postIdStr, userIdStr);
            postEntity.decreaseLikeCount();
            return false;
        } else {
            // 좋아요 추가
            redisService.addLike(postIdStr, userIdStr);
            postEntity.increaseLikeCount();
            return true;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> getLikedPosts(Long userId) {
        Set<String> likedPostIds = redisService.getLikedPostsByUserId(String.valueOf(userId));

        List<Long> postIds = likedPostIds.stream()
                .map(Long::valueOf)
                .toList();

        List<PostEntity> likedPosts = postRepository.findAllById(postIds);

        return likedPosts.stream()
                .map(PostMapper::toDTO)
                .toList();
    }
}
