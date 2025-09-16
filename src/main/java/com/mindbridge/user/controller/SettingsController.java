package com.mindbridge.user.controller;

import com.mindbridge.user.domain.User;
import com.mindbridge.user.domain.UserSettings;
import com.mindbridge.user.dto.SettingsDto;
import com.mindbridge.user.repository.UserRepository;
import com.mindbridge.user.repository.UserSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/me/settings")
public class SettingsController {

    private final UserRepository users; // 사용자
    private final UserSettingsRepository settingsRepo; // crud

    // 기본값 (조회)
    @GetMapping
    public SettingsDto get() {
        Long userId = 1L; // jmt 교체
        users.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다: " + userId));

        return settingsRepo.findById(userId)
                .map(this::toDto)
                .orElse(new SettingsDto(false, false, true, "ko"));
                             // 없으면 DB에 저장하지 않고 기본값 DTO 반환
    }

    // 생성 및 조회 (부분 업데이트) !
    @PatchMapping
    @Transactional
    public SettingsDto patch(@RequestBody SettingsDto req) {
        Long userId = 1L; // jmt 교체
        User user = users.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다: " + userId));

        UserSettings s = settingsRepo.findById(userId)
                .orElseGet(() -> UserSettings.builder().user(user).build());
        // 기존 설정이 있으면 가져오고, 없으면 새 엔티티를 메모리에서 생성

        if (req.pushEnabled()     != null) s.setPushEnabled(req.pushEnabled());
        if (req.emailEnabled()    != null) s.setEmailEnabled(req.emailEnabled());
        if (req.privateProfile()  != null) s.setPrivateProfile(req.privateProfile());

        if (req.language()        != null) {
            String lang = req.language();
            if (!"ko".equals(lang) && !"en".equals(lang)) {
                throw new IllegalArgumentException("언어는 ko|en만 허용됩니다.");
            }
            s.setLanguage(lang);
        }

        settingsRepo.save(s); // 신규면 insert, 기존이면 update
        return toDto(s);
    }

    private SettingsDto toDto(UserSettings s) {
        return new SettingsDto(
                s.isPushEnabled(),
                s.isEmailEnabled(),
                s.isPrivateProfile(),
                s.getLanguage()
        );
    }
}
