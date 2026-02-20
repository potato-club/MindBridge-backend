package com.mindbridge.repository;

import com.mindbridge.dto.ResponseDto.post.PostListResponseDto;
import com.mindbridge.dto.ResponseDto.post.PostResponseDto;
import com.mindbridge.entity.PostEntity;
import com.mindbridge.entity.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("""
       SELECT new com.mindbridge.dto.ResponseDto.PostListResponseDto(
           id,
           user.username,
           user.nickname,
           user.profileImage,
           title,
           contents,
           isAnonymous,
           createdAt,
           viewCount,
           likeCount,
           commentCount
       )
       FROM PostEntity
       WHERE (:category = 'ALL' OR category = :category)
       ORDER BY id DESC
       """)
    Page<PostListResponseDto> findByCategory(@Param("category") Category category, PageRequest pageRequest);

    @Query("""
    SELECT new com.mindbridge.dto.ResponseDto.PostResponseDto(
           id,
           category,
           user.username,
           user.profileImage,
           user.nickname,
           title,
           contents,
           isAnonymous,
           createdAt,
           viewCount,
           likeCount,
           commentCount
    )
    FROM PostEntity
    WHERE id = :postId
    """)
    Optional<PostResponseDto> findPostWithWriter(@Param("postId") long postId);

    @Query("""
        SELECT new com.mindbridge.dto.ResponseDto.PostListResponseDto(
           id,
           user.username,
           user.nickname,
           user.profileImage,
           title,
           contents,
           isAnonymous,
           createdAt,
           viewCount,
           likeCount,
           commentCount
       )
        FROM PostEntity
        WHERE (:category = 'ALL' OR category = :category)
          AND (LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(contents) LIKE LOWER(CONCAT('%', :keyword, '%')))
        ORDER BY id DESC
    """)
    Page<PostListResponseDto> searchPostsByKeyword(
            @Param("category") Category category,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    List<PostEntity> findTop3ByCreatedAtAfterOrderByViewCountDesc(LocalDateTime oneWeekAgo);
}