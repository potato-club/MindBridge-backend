package com.mindbridge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIdRequestDto {
    private String username;
    private String phoneNumber;
    private String code;
}
