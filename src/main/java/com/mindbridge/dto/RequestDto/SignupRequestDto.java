package com.mindbridge.dto.RequestDto;

import com.mindbridge.entity.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupRequestDto {
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

    @NotNull
    private Gender gender;

    @Min(1925)
    @Max(2025)
    private LocalDate birthDate;

}
