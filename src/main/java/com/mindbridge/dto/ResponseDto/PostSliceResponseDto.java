package com.mindbridge.dto.ResponseDto;

import com.mindbridge.entity.enums.Category;

import java.util.List;

public record PostSliceResponseDto<T>(
        Category category,
        List<T> contents,
        boolean hasNext
) {}
