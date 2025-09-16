package com.mindbridge.repository;

import com.mindbridge.entity.CommentEntity;
import com.mindbridge.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPostIdAndParentIsNull(PostEntity postId);
    @Modifying
    @Query("UPDATE CommentEntity c set c.parent = null WHERE c.parent.id = :parentId")
    void updateParentToNullByParentId(@Param("parentId") Long parentId);
}
