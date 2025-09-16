package com.mindbridge.user.controller;

import com.mindbridge.user.domain.Reaction;
import com.mindbridge.user.domain.ReactionType;
import com.mindbridge.user.dto.BookmarkItemDto;
import com.mindbridge.user.dto.PostItemDto;
import com.mindbridge.user.dto.ReactionItemDto;
import com.mindbridge.user.repository.BookmarkRepository;
import com.mindbridge.user.repository.PostRepository;
import com.mindbridge.user.repository.ReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor

@RequestMapping("/me")
public class MyActivityController {

    private final PostRepository posts;
    private final BookmarkRepository bookmarks;
    private final ReactionRepository reactions;

    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    // 내가 쓴 글 목록
    @GetMapping("/posts")
    public Page<PostItemDto> myPosts(@RequestParam(defaultValue = "0")  int page,
                                     @RequestParam(defaultValue = "20") int size) {
        if (page < 0 || size <= 0) throw new IllegalArgumentException("page>=0, size>0 이어야 합니다.");
        Long userId = 1L; // JMT 교체
        var p = posts.findByAuthorIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size));
        return p.map(v -> new PostItemDto(
                v.getId(),
                v.getTitle(),
                v.getCreatedAt() == null ? null : v.getCreatedAt().format(F)
        ));
    }


    // 내가 북마크한 글 목록
    @GetMapping("/bookmarks")
    public Page<BookmarkItemDto> myBookmarks(@RequestParam(defaultValue = "0")  int page,
                                             @RequestParam(defaultValue = "20") int size) {
        if (page < 0 || size <= 0) throw new IllegalArgumentException("page>=0, size>0 이어야 합니다.");
        Long userId = 1L; // JMt 교채
        var p = bookmarks.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size));
        return p.map(v -> new BookmarkItemDto(
                v.getPost().getId(),
                v.getPost().getTitle(),
                v.getCreatedAt() == null ? null : v.getCreatedAt().format(F)
        ));
    }

    // 내가 반응(좋아요/싫어요)한 글 목록
    @GetMapping("/reactions")
    public Page<ReactionItemDto> myReactions(@RequestParam(defaultValue = "ALL") String type,
                                             @RequestParam(defaultValue = "0")   int page,
                                             @RequestParam(defaultValue = "20")  int size) {
        if (page < 0 || size <= 0) throw new IllegalArgumentException("page>=0, size>0 이어야 합니다.");
        Long userId = 1L; // JMT 교채
        Page<Reaction> p;
        if ("ALL".equalsIgnoreCase(type)) {
            p = reactions.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size));
        } else {             // ReactionType enum으로 변환
            ReactionType t;
            try {
                t = ReactionType.valueOf(type.toUpperCase()); // LIKE / DISLIKE
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("type은 ALL|LIKE|DISLIKE 중 하나여야 합니다."); //실패 시 400응답
            }
            p = reactions.findByUserIdAndTypeOrderByCreatedAtDesc(userId, t, PageRequest.of(page, size));
        }
        return p.map(v -> new ReactionItemDto(
                v.getPost().getId(),
                v.getPost().getTitle(),
                v.getType().name(),
                v.getCreatedAt() == null ? null : v.getCreatedAt().format(F)
        ));
    }
}
