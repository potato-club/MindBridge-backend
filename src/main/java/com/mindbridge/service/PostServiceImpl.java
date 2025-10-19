package com.mindbridge.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostRedisService redisService;

    @Override
    @Transactional
    public PostResponseDto createPost(PostCreateRequestDto requestDTO) {
        PostEntity post = postMapper.toEntity(requestDTO);
        PostEntity saved  = postRepository.save(post);
        return postMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PostSliceResponseDto<PostListResponseDto> getAllPosts(Category category, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Slice<PostListResponseDto> posts = postRepository.findByCategory(category, pageRequest);

        return new PostSliceResponseDto<>(posts.getContent(), posts.hasNext());
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
    public PostSliceResponseDto<PostListResponseDto> searchPosts(Category category, String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Slice<PostListResponseDto> posts = postRepository.searchPostsByKeyword( category, keyword, pageRequest);

        return new PostSliceResponseDto<>(posts.getContent(), posts.hasNext());
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
        return postMapper.toDTO(postEntity);
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
                .map(postMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public List<PostResponseDto> getWeeklyTopPosts() {
        LocalDateTime oneWeekAge = LocalDateTime.now().minusDays(7);

        List<PostEntity> topPosts =
                postRepository.findTop3ByCreatedAtAfterOrderByViewCountDesc(oneWeekAge);

        return postMapper.toDTOList(topPosts);
    }
}
