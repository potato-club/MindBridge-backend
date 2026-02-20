package com.mindbridge.controller.post;

import com.mindbridge.dto.ResponseDto.post.PostPopularResponseDto;
import com.mindbridge.service.post.Popular.PostPopularService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostPopularController {
    private final PostPopularService postPopularService;

    @Operation(summary = "주간 인기 게시물 by 조민기")
    @GetMapping("/popular/weekly")
    public ResponseEntity<List<PostPopularResponseDto>> getWeeklyTopPosts() {
        return ResponseEntity.ok(postPopularService.getWeeklyTopPosts());
    }
}
