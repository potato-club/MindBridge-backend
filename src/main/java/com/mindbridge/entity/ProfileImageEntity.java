package com.mindbridge.entity;

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
@Table(name = "profile_photo")
public class ProfileImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "profileUrl", nullable = false, length = 255)
    private String url;

    @Column(name = "recorded_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime recordedAt;

    public void setUrl(String url) {

    }
}
