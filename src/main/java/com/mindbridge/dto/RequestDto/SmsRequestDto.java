package com.mindbridge.dto.RequestDto;

import java.util.List;

public record SmsRequestDto(
   String type,
   String contentType,
   String countryCode,
   String from,
   String content,
   List<MessageRequestDto> messages
) {}
