package com.example.mindbridge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordRequestDTO {
    private String userid;
    private String phoneNumber;
    private String code;
}
