package com.mindbridge.dto;

import jakarta.validation.constraints.Pattern;

public record ResetPasswordRequestDto (
    String userid,

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 각각 최소 1개씩 포함해야 합니다."
    )
    String newPassword,
    String confirmPassword
) {}
