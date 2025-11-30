package com.mindbridge.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId;


    @Column(name = "user_id")
    private Long userId;


    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_anonymous")
    private Boolean isAnonymous;

    @Column(name = "like_count")
    private int likeCount;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void update (String content) {
        this.content = content;
    }

}
