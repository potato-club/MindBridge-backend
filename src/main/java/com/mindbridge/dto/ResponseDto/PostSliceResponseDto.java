package com.mindbridge.dto.ResponseDto;

import java.util.List;

public record PostSliceResponseDto<T>(
   List<T> contents,
   boolean hasNext
) {}
