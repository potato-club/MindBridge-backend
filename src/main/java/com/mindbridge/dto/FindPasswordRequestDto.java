package com.mindbridge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordRequestDto {
    private String userid;
    private String phoneNumber;
    private String code;
}
