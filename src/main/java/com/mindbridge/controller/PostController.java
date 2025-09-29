package com.mindbridge.controller;


import com.mindbridge.dto.RequestDto.PostCreateRequestDto;
import com.mindbridge.dto.ResponseDto.PostResponseDto;
import com.mindbridge.dto.RequestDto.PostUpdateRequestDto;
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
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }


    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId,
                                                      @RequestBody PostUpdateRequestDto requestDTO) {
        return ResponseEntity.ok(postService.updatePost(postId, requestDTO));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{postId}/like/{userId}")
    public ResponseEntity<Boolean> toggleLike(@PathVariable Long postId,@PathVariable Long userId) {
        boolean liked = postService.toggleLike(postId, userId);
        return ResponseEntity.ok(liked);
    }

    @GetMapping("/liked/{userId}")
    public ResponseEntity<List<PostResponseDto>> getLikedPosts(@PathVariable Long userId) {
        List<PostResponseDto> likedPosts = postService.getLikedPosts(userId);
        return ResponseEntity.ok(likedPosts);
    }
}