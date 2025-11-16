package com.mindbridge.service.post.CRUD;

import com.mindbridge.dto.RequestDto.PostCreateRequestDto;
import com.mindbridge.dto.ResponseDto.PostListResponseDto;
import com.mindbridge.dto.ResponseDto.PostResponseDto;
import com.mindbridge.dto.RequestDto.PostUpdateRequestDto;
import com.mindbridge.dto.ResponseDto.PostSliceResponseDto;
import com.mindbridge.entity.PostEntity;
import com.mindbridge.entity.enums.Category;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.PostNotFoundException;
import com.mindbridge.mapper.PostMapper;
import com.mindbridge.repository.PostRepository;
import com.mindbridge.service.post.PostRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostRedisService redisService;

    @Override
    @Transactional
    public PostResponseDto createPost(long userId, PostCreateRequestDto requestDTO) {
        PostEntity post = postMapper.toEntity(userId, requestDTO);
        PostEntity saved  = postRepository.save(post);
        return postMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PostSliceResponseDto<PostListResponseDto> getAllPosts(Category category, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Slice<PostListResponseDto> posts = postRepository.findByCategory(category, pageRequest);

        return new PostSliceResponseDto<>(category, posts.getContent(), posts.hasNext());
    }

    @Override
    @Transactional
    public PostResponseDto getPost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        postEntity.increaseViewCount();

        int redisLikeCount = redisService.getLikeCount(String.valueOf(postId));
        int redisCommentCount = redisService.getCommentCount(String.valueOf(postId));

        postEntity.setLikeCount(postEntity.getLikeCount() + redisLikeCount);
        postEntity.setCommentCount(postEntity.getCommentCount() + redisCommentCount);
        return postMapper.toDTO(postEntity);
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(long postId, PostUpdateRequestDto postUpdateRequestDto) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        postEntity.update(
                postUpdateRequestDto.title(),
                postUpdateRequestDto.content()
        );
        return postRepository.findPostWithWriter(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}