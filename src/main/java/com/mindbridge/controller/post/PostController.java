package com.mindbridge.controller.post;

import com.mindbridge.dto.RequestDto.PostCreateRequestDto;
import com.mindbridge.dto.RequestDto.PostUpdateRequestDto;
import com.mindbridge.dto.ResponseDto.PostListResponseDto;
import com.mindbridge.dto.ResponseDto.PostResponseDto;
import com.mindbridge.dto.ResponseDto.PageResponseDto;
import com.mindbridge.entity.enums.Category;
import com.mindbridge.jwt.CustomUserDetails;
import com.mindbridge.service.post.CRUD.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 생성 by 조민기")
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PostCreateRequestDto requestDTO) {

        long userId = userDetails.getId();

        return ResponseEntity.ok(postService.createPost(userId, requestDTO));
    }

    @Operation(summary = "전체 게시글 조회 by 조민기")
    @GetMapping
    public ResponseEntity<PageResponseDto<PostListResponseDto>> getAllPosts(
            @RequestParam Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
            ) {
        PageResponseDto<PostListResponseDto> postResponseDto = postService.getAllPosts(category, page, size);
        return ResponseEntity.ok(postResponseDto);
    }

    @Operation(summary = "단일 게시글 조회 by 조민기")
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(
            @PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }


    @Operation(summary = "게시글 수정 by 조민기")
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto requestDto) {
        return ResponseEntity.ok(postService.updatePost(postId, requestDto));
    }

    @Operation(summary = "게시글 삭제 by 조민기")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        long userId = userDetails.getId();

        postService.deletePost(postId, userId);

        return ResponseEntity.noContent().build();
    }
}