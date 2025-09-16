package com.mindbridge.user.controller;

import com.mindbridge.user.domain.User;
import com.mindbridge.user.dto.MeSummaryDto;
import com.mindbridge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class MeController {

    private final UserRepository users;

    // 내 정보 조회
    @GetMapping("/me")
    public MeSummaryDto me() {
        Long userId = 1L; // JMY 교체
        User u = users.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다: " + userId)); // 404 매핑
        return new MeSummaryDto(u.getId(), u.getNickname(), u.getEmail());
    }

    // 회원 탈퇴
    @DeleteMapping("/me")
    @Transactional // 예외 시 롤백 어노
    public ResponseEntity<Void> deleteMe() {
        Long userId = 1L; // JMT 교체
        User u = users.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다: " + userId));

        if (u.getDeletedAt() == null) {
            u.setDeletedAt(LocalDateTime.now());
            users.save(u);
        }
        return ResponseEntity.noContent().build(); // 204 응답
    }
}
