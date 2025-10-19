package com.mindbridge.repository;

import com.mindbridge.dto.ResponseDto.PostListResponseDto;
import com.mindbridge.entity.PostEntity;
import com.mindbridge.entity.enums.Category;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("""
       SELECT new com.mindbridge.dto.ResponseDto.PostListResponseDto(
           p.id,
           p.user.username,
           p.user.nickname,
           p.category,
           p.title,
           p.contents,
           p.isAnonymous,
           p.createdAt,
           p.viewCount,
           p.likeCount,
           p.commentCount
       )
       FROM PostEntity p
       WHERE (:category = 'ALL' OR p.category = :category)
       ORDER BY p.id DESC
       """)
    Slice<PostListResponseDto> findByCategory(@Param("category") Category category, PageRequest pageRequest);

    @Query("""
        SELECT new com.mindbridge.dto.ResponseDto.PostListResponseDto(
           p.id,
           p.user.username,
           p.user.nickname,
           p.category,
           p.title,
           p.contents,
           p.isAnonymous,
           p.createdAt,
           p.viewCount,
           p.likeCount,
           p.commentCount
       )
        FROM PostEntity p
        WHERE (:category = 'ALL' OR p.category = :category)
          AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(p.contents) LIKE LOWER(CONCAT('%', :keyword, '%')))
        ORDER BY p.id DESC
    """)
    Slice<PostListResponseDto> searchPostsByKeyword(
            @Param("category") Category category,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    List<PostEntity> findTop3ByCreatedAtAfterOrderByViewCountDesc(LocalDateTime oneWeekAgo);
}