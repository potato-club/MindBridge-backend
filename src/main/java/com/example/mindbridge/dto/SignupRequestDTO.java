package com.example.mindbridge.dto;

import com.example.mindbridge.entity.enums.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupRequestDTO {
    @NotBlank
    @Size(min = 2, max = 20)
    private String loginId;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 2, max = 20)
    private String nickname;

    @NotBlank
    @Size(min = 5, max = 16)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private Gender gender;

    @Min(1925)
    @Max(2025)
    private LocalDate birthDate;

}
