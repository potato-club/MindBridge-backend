package com.mindbridge.entity.user;

import com.mindbridge.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_image_id", nullable = true)
    private Long profileImageId;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Column(name = "login_id", nullable = false, length = 50)
    private String loginId;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "phonenum", length = 30)
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean isDeleted = false;
}