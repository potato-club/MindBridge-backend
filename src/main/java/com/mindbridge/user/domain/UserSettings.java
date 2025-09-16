package com.mindbridge.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name="user_settings")
public class UserSettings {

    // PK = users.id 와 동일한 값
    @Id
    @Column(name = "user_id")
    private Long userId;

    @MapsId // 위 user_id를 User의 id로부터 매핑
    @OneToOne(fetch = FetchType.LAZY)      // 지연로딩~
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable=false) private boolean pushEnabled = false;
    @Column(nullable=false) private boolean emailEnabled = false;
    @Column(nullable=false) private boolean privateProfile = true;

    @Column(length=10) private String language = "ko";
}
