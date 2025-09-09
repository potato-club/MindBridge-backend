package com.mindbridge.repository;

import com.mindbridge.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPostIdAndParentIsNull(Long postId);
}
