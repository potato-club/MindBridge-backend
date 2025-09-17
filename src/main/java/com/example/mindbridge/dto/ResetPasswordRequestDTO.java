package com.example.mindbridge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDTO {
    private String userid;
    private String newPassword;
    private String confirmPassword;
}
