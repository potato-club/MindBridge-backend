package com.mindbridge.dto;

public record FindPasswordRequestDto (
    String userid,
    String phoneNumber
) {}
