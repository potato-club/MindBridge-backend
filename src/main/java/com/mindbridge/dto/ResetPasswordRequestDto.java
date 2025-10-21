package com.mindbridge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDto {
    private String userid;
    private String newPassword;
    private String confirmPassword;
}
