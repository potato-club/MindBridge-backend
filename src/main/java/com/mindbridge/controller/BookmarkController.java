package com.mindbridge.controller;

import com.mindbridge.jwt.CustomUserDetails;
import com.mindbridge.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> addBookmark(
            @AuthenticationPrincipal CustomUserDetails customUserDetail,
            @PathVariable Long postId) {

        Long userId = customUserDetail.getId();

        bookmarkService.addBookmark(userId, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Boolean> isBookmarked(
            @AuthenticationPrincipal CustomUserDetails customUserDetail,
            @PathVariable Long postId) {

        Long userId = customUserDetail.getId();

        boolean result = bookmarkService.isBookmarked(userId, postId);
        return ResponseEntity.ok(result);
    }

}
