package com.mindbridge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequestDto (
    @NotBlank(message = "아이디는 필수값입니다.")
    String userid,

    @NotBlank(message = "새 비밀번호는 필수값입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 각각 최소 1개씩 포함해야 합니다."
    )
    String newPassword,
    String confirmPassword
) {}
