package com.mindbridge.controller;

import com.mindbridge.dto.RequestDTO.PostCommentCreateRequestDTO;
import com.mindbridge.dto.ResponseDTO.PostCommentResponseDTO;
import com.mindbridge.dto.RequestDTO.PostCommentUpdateRequestDTO;
import com.mindbridge.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class PostCommentController {
    private final PostCommentService postCommentService;

    @PostMapping
    public ResponseEntity<PostCommentResponseDTO> createComment(@RequestBody PostCommentCreateRequestDTO postCommentCreateRequestDTO) {
        return ResponseEntity.ok(postCommentService.createComment(postCommentCreateRequestDTO));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<PostCommentResponseDTO>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postCommentService.getCommentsByPost(postId));
    }

    @GetMapping("/{postId}/replies")
    public ResponseEntity<List<PostCommentResponseDTO>> getCommentsWithRepliesByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postCommentService.getCommentsWithRepliesByPost(postId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostCommentResponseDTO> updateComment(
            @PathVariable Long id,
            @RequestBody PostCommentUpdateRequestDTO postCommentUpdateRequestDTO) {
        return ResponseEntity.ok(postCommentService.updateComment(id, postCommentUpdateRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        postCommentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
