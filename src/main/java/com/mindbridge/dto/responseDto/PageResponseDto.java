package com.mindbridge.dto.ResponseDto;

import com.mindbridge.entity.enums.Category;

import java.util.List;

public record PageResponseDto<T>(
        Category category,
        List<T> contents,
        int page,
        int size,
        long totalElements,
        int totalPage,
        boolean last
) {}
