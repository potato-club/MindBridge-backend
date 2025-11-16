package com.mindbridge.controller.post;

import com.mindbridge.dto.ResponseDto.PostResponseDto;
import com.mindbridge.jwt.CustomUserDetails;
import com.mindbridge.service.post.Like.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @Operation(summary = "좋아요 by 조민기")
    @PostMapping("/{postId}/liked")
    public ResponseEntity<Boolean> toggleLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        long userId = userDetails.getId();

        boolean liked = postLikeService.toggleLike(postId, userId);
        return ResponseEntity.ok(liked);
    }

    @Operation(summary = "게시글 좋아요 조회 by 조민기")
    @GetMapping("/liked/{userId}")
    public ResponseEntity<List<PostResponseDto>> getLikedPosts(
            @PathVariable Long userId) {
        List<PostResponseDto> likedPosts = postLikeService.getLikedPosts(userId);
        return ResponseEntity.ok(likedPosts);
    }
}
