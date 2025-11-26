package com.mindbridge.controller;

import com.mindbridge.dto.RequestDto.ProfileImageRequestDto;
import com.mindbridge.dto.ResponseDto.ProfileImageResponseDto;
import com.mindbridge.entity.ProfileImageEntity;
import com.mindbridge.jwt.CustomUserDetails;
import com.mindbridge.service.ProfileImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/profileImage")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ProfileImageService profileImageService;

    @Operation(summary = "프로필 이미지 조회 by 조민기")
    @GetMapping
    public ResponseEntity<ProfileImageResponseDto> getMainProfilePhoto(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        long userId = userDetails.getId();

        return ResponseEntity.ok(profileImageService.getMainProfileImage(userId));
    }

    @Operation(summary = "프로필 이미지 히스토리 by 조민기")
    @GetMapping("/history")
    public ResponseEntity<List<ProfileImageEntity>> getProfileImages(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        long userId = userDetails.getId();

        List<ProfileImageEntity> profileImageEntityList = profileImageService.getProfileImages(userId);
        return ResponseEntity.ok(profileImageEntityList);
    }

    @Operation(summary = "프로필 이미지 업로드 by 조민기")
    @PostMapping()
    public ResponseEntity<List<String>> uploadProfileImage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        long userId = userDetails.getId();
        String url = profileImageService.uploadProfileImage(image, userId);
        System.out.println("USER ID = " + userId);
        return ResponseEntity.ok(List.of(url));
    }

    @Operation(summary = "이미지 삭제 by 조민기")
    @DeleteMapping
    public ResponseEntity<String> deleteImage(
            @RequestBody ProfileImageRequestDto dto) {

        profileImageService.deleteImage(dto);
        return ResponseEntity.ok("Body image deleted successfully.");
    }
}