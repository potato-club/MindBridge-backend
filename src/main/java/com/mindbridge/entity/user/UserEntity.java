package com.mindbridge.entity.user;

import com.mindbridge.entity.enums.Gender;
import com.mindbridge.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity extends BaseEntity{

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_num")
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean verified;

    @Column(name = "mileage")
    private int mileage;

    @Column(length = 500)
    private String refreshToken;

    private LocalDateTime refreshTokenExpiredAt;

    public void updateRefreshToken(String refreshToken, LocalDateTime expiredAt) {
        this.refreshToken = refreshToken;
        this.refreshTokenExpiredAt = expiredAt;
    }
}