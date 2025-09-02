package com.example.mindbridge.controller;


import com.example.mindbridge.dto.PostCreateRequestDTO;
import com.example.mindbridge.dto.PostResponseDTO;
import com.example.mindbridge.dto.PostUpdateRequestDTO;
import com.example.mindbridge.service.PostService;
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
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostCreateRequestDTO requestDTO) {
        return ResponseEntity.ok(postService.createPost(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long id,
                                                      @RequestBody PostUpdateRequestDTO requestDTO) {
        return ResponseEntity.ok(postService.updatePost(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}