package com.mindbridge.service.post.Like;

import com.mindbridge.dto.ResponseDto.post.PostResponseDto;
import com.mindbridge.entity.PostEntity;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.PostNotFoundException;
import com.mindbridge.mapper.PostMapper;
import com.mindbridge.repository.PostRepository;
import com.mindbridge.service.post.PostRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService{
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostRedisService redisService;

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
}
