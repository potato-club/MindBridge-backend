package com.mindbridge.controller.post;

import com.mindbridge.dto.ResponseDto.PostListResponseDto;
import com.mindbridge.dto.ResponseDto.PageResponseDto;
import com.mindbridge.entity.enums.Category;
import com.mindbridge.service.post.Search.PostSearchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostSearchController {
    private final PostSearchService postSearchService;

    @Operation(summary = "게시글 검색 by 조민기")
    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<PostListResponseDto>> searchPosts(
            @RequestParam Category category,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        PageResponseDto<PostListResponseDto> postResponseDTOs = postSearchService.searchPosts(category, keyword, page, size);

        return ResponseEntity.ok(postResponseDTOs);
    }

}
