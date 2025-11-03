package com.mindbridge.dto.ResponseDto;

import java.time.LocalDateTime;

public record SmsResponseDto(
   String requestId,
   LocalDateTime requestTime,
   String statusCode,
   String statusName
) {}
