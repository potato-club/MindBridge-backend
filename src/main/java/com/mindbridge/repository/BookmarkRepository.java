package com.mindbridge.repository;

import com.mindbridge.entity.BookmarkEntity;
import com.mindbridge.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    void deleteByUserIdAndPostId(Long userId, Long postId);

    @Query("SELECT post FROM PostEntity post JOIN BookmarkEntity bookmark on post.id = bookmark.id WHERE bookmark.userId = :userId")
    List<PostEntity> findBookmarkedPostsByUserId(@Param("userId")  Long userId);
}
