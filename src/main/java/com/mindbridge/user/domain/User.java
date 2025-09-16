package com.mindbridge.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name="users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(nullable=false, unique=true, length=255) private String email;
    @Column(nullable=false, length=255) private String passwordHash;
    @Column(nullable=false, length=60)  private String nickname;

    @Column(nullable=false) private boolean anonymousDefault = true;
    @Column(nullable=false) private boolean blocked = false;

    private LocalDateTime deletedAt;

    @CreationTimestamp @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;
}
