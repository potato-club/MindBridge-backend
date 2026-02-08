package com.mindbridge.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(length = 500 ,nullable = false)
    private String refreshToken;

    private LocalDateTime expiredAt;

    private String token;
    private LocalDateTime createdAt;

    public void update(String refreshToken, LocalDateTime expiresAt) {
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
    }
}
