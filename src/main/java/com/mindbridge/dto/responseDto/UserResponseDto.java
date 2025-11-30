package com.mindbridge.dto.responseDto;

import com.mindbridge.entity.user.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String loginId;
    private final String nickname;
    private final String username;

    public UserResponseDto(@NotNull UserEntity user) {
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.nickname = user.getNickname();
        this.username = user.getUsername();
    }
}
