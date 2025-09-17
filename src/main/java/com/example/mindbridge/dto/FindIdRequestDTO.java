package com.example.mindbridge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIdRequestDTO {
    private String username;
    private String phoneNumber;
    private String code;
}
