package com.mindbridge.controller;

import com.mindbridge.dto.RequestDto.PostCreateRequestDto;
import com.mindbridge.dto.ResponseDto.PostListResponseDto;
import com.mindbridge.dto.ResponseDto.PostResponseDto;
import com.mindbridge.dto.RequestDto.PostUpdateRequestDto;
import com.mindbridge.dto.ResponseDto.PostSliceResponseDto;
import com.mindbridge.entity.enums.Category;
import com.mindbridge.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody PostCreateRequestDto requestDTO) {
        return ResponseEntity.ok(postService.createPost(requestDTO));
    }

    @GetMapping
    public ResponseEntity<PostSliceResponseDto<PostListResponseDto>> getAllPosts(
            @RequestParam Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
            ) {
        PostSliceResponseDto<PostListResponseDto> postResponseDto = postService.getAllPosts(category, page, size);
        return ResponseEntity.ok(postResponseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(
            @PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @GetMapping("/search")
    public ResponseEntity<PostSliceResponseDto<PostListResponseDto>> searchPosts(
            @RequestParam Category category,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        PostSliceResponseDto<PostListResponseDto> postResponseDTOs = postService.searchPosts(category, keyword, page, size);

        return ResponseEntity.ok(postResponseDTOs);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto requestDTO) {
        return ResponseEntity.ok(postService.updatePost(postId, requestDTO));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{postId}/like/{userId}")
    public ResponseEntity<Boolean> toggleLike(
            @PathVariable Long postId,
            @PathVariable Long userId) {
        boolean liked = postService.toggleLike(postId, userId);
        return ResponseEntity.ok(liked);
    }

    @GetMapping("/liked/{userId}")
    public ResponseEntity<List<PostResponseDto>> getLikedPosts(
            @PathVariable Long userId) {
        List<PostResponseDto> likedPosts = postService.getLikedPosts(userId);
        return ResponseEntity.ok(likedPosts);
    }

    @GetMapping("/popular/weekly")
    public ResponseEntity<List<PostResponseDto>> getWeeklyTopPosts() {
        return ResponseEntity.ok(postService.getWeeklyTopPosts());
    }
}