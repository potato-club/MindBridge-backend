package com.mindbridge.service;

import com.mindbridge.dto.ResponseDto.post.PostResponseDto;
import com.mindbridge.entity.BookmarkEntity;
import com.mindbridge.entity.PostEntity;
import com.mindbridge.entity.UserEntity;
import com.mindbridge.mapper.PostMapper;
import com.mindbridge.repository.BookmarkRepository;
import com.mindbridge.repository.PostRepository;
import com.mindbridge.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService{
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final RedisTemplate<String, String> redisTemplate;

    private String getKey(Long userId, Long postId)  {
        return "bookmark:" + userId + ":" + postId;
    }

    @Override
    @Transactional
    public void addBookmark(Long userId, Long postId) {
        String key = getKey(userId, postId);

        if (Boolean.TRUE.equals(redisTemplate.hasKey(key)) ||
                bookmarkRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new IllegalStateException("이미 북마크한 게시글입니다.");
        }

        UserEntity user = userRepository.getReferenceById(userId);

        PostEntity post = postRepository.getReferenceById(postId);


        BookmarkEntity bookmarkEntity = BookmarkEntity.builder()
                .user(user)
                .post(post)
                .build();
        bookmarkRepository.save(bookmarkEntity);

        redisTemplate.opsForValue().set(key, "1");
    }

    @Override
    @Transactional
    public boolean isBookmarked(Long userId, Long postId) {
        String key = getKey(userId, postId);

        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))){
            return true;
        }

        boolean  exists = bookmarkRepository.existsByUserIdAndPostId(userId, postId);

        if(exists) {
            redisTemplate.opsForValue().set(key, "1");
        }
        return  exists;
    }

    @Override
    @Transactional
    public void removeBookmark(Long userId, Long postId) {
        bookmarkRepository.deleteByUserIdAndPostId(userId, postId);

        redisTemplate.delete(getKey(userId, postId));
    }

    @Override
    public List<PostResponseDto> getBookmarkedPosts(Long userId) {
        List<PostEntity> postEntityList =
                bookmarkRepository.findBookmarkedPostsByUserId(userId);

        return postEntityList.stream()
                .map(postMapper::toDTO)
                .toList();
    }

}
