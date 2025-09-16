package com.mindbridge.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name="reactions",
        uniqueConstraints = @UniqueConstraint(name="uq_reactions_user_post", columnNames = {"user_id","post_id"}),
        indexes = @Index(name="idx_reactions_user_created", columnList = "user_id, created_at"))
public class Reaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable=false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable=false)
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=7)
    private ReactionType type; // LIKE / DISLIKE

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;
}
