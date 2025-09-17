package com.example.mindbridge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyCodeRequestDTO {
    private String phoneNumber;
    private String code;
}
