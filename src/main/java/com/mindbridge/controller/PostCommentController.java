package com.mindbridge.controller;

import com.mindbridge.dto.RequestDto.PostCommentCreateRequestDto;
import com.mindbridge.dto.RequestDto.PostCommentUpdateRequestDto;
import com.mindbridge.dto.ResponseDto.PostCommentResponseDto;
import com.mindbridge.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostCommentController {
    private final PostCommentService postCommentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<PostCommentResponseDto> createComment(
            @PathVariable Long postId,
            @RequestBody PostCommentCreateRequestDto postCommentCreateRequestDto) {
        PostCommentResponseDto postCommentResponseDto = postCommentService.createComment(postId, postCommentCreateRequestDto);
        return ResponseEntity.ok(postCommentResponseDto);
    }

    @PatchMapping("/comments/{id}")
    public ResponseEntity<PostCommentResponseDto> updateComment(
            @PathVariable Long id,
            @RequestBody PostCommentUpdateRequestDto dto) {
        return ResponseEntity.ok(postCommentService.updateComment(id, dto));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        postCommentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comments/{id}/like/{userId}")
    public ResponseEntity<Boolean> likeComment(
            @PathVariable Long id,
            @PathVariable Long userId) {
        postCommentService.likeComment(id, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/comments/{id}/unlike/{userId}")
    public ResponseEntity<Void> unlikeComment(
            @PathVariable Long id,
            @PathVariable Long userId) {
        postCommentService.unlikeComment(id, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostCommentResponseDto>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postCommentService.getCommentsByPost(postId));
    }
}
