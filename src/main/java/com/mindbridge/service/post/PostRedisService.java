package com.mindbridge.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostRedisService {
    private final StringRedisTemplate redisTemplate;

    private static final String POST_LIKES_KEY = "likes:post:";   // 게시글별 좋아요
    private static final String USER_LIKES_KEY = "likes:user:";   // 유저별 좋아요
    private static final String POST_COMMENT_COUNT_KEY = "comments:posts";

    // 좋아요 추가
    public boolean addLike(String postId, String userId) {
        Long addedToPost = redisTemplate.opsForSet().add(POST_LIKES_KEY + postId, userId);
        Long addedToUser = redisTemplate.opsForSet().add(USER_LIKES_KEY + userId, postId);
        return (addedToPost != null && addedToPost > 0) || (addedToUser != null && addedToUser > 0);
    }

    // 좋아요 취소
    public boolean removeLike(String postId, String userId) {
        Long removedFromPost = redisTemplate.opsForSet().remove(POST_LIKES_KEY + postId, userId);
        Long removedFromUser = redisTemplate.opsForSet().remove(USER_LIKES_KEY + userId, postId);
        return (removedFromPost != null && removedFromPost > 0) || (removedFromUser != null && removedFromUser > 0);
    }

    // 특정 사용자가 특정 게시물에 좋아요를 눌렀는지 확인
    public boolean hasLiked(String postId, String userId) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(POST_LIKES_KEY + postId, userId));
    }

    // 게시물 총 좋아요 수 조회
    public int getLikeCount(String postId) {
        Long count = redisTemplate.opsForSet().size(POST_LIKES_KEY + postId);
        return count != null ? count.intValue() : 0;
    }

    // 유저가 좋아요한 게시글 ID 목록 조회
    public Set<String> getLikedPostsByUserId(String userId) {
        return redisTemplate.opsForSet().members(USER_LIKES_KEY + userId);
    }

    //댓글 수 증가
    public void incrementCommentCount(String postId) {
        redisTemplate.opsForValue().increment(POST_COMMENT_COUNT_KEY + postId);
    }

    //댓글 수 감소
    public void decrementCommentCount(String postId) {
        redisTemplate.opsForValue().decrement(POST_COMMENT_COUNT_KEY + postId);
    }

    //댓글 수 조회
    public int getCommentCount(String postId) {
        String countStr = redisTemplate.opsForValue().get(POST_COMMENT_COUNT_KEY + postId);
        return countStr != null ? Integer.parseInt(countStr) : 0;
    }
}