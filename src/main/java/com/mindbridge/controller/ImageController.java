package com.mindbridge.controller;

import com.mindbridge.dto.RequestDto.ProfileImageRequestDto;
import com.mindbridge.dto.ResponseDto.ProfileImageResponseDto;
import com.mindbridge.entity.ProfileImageEntity;
import com.mindbridge.jwt.CustomUserDetails;
import com.mindbridge.service.ProfileImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/body-image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ProfileImageService profileImageService;

    @GetMapping
    public ResponseEntity<ProfileImageResponseDto> getMainProfilePhoto(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        long userId = userDetails.getId();

        return ResponseEntity.ok(profileImageService.getMainProfileImage(userId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<ProfileImageEntity>> getProfileImages(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        long userId = userDetails.getId();

        List<ProfileImageEntity> profileImageEntityList = profileImageService.getProfileImages(userId);
        return ResponseEntity.ok(profileImageEntityList);
    }

    @PostMapping()
    public ResponseEntity<ProfileImageRequestDto> uploadProfileImage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        long userId = userDetails.getId();
        String url = profileImageService.uploadProfileImage(image, userId);

        return ResponseEntity.ok(new ProfileImageRequestDto(url));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteImage(
            @RequestBody ProfileImageRequestDto dto) {

        profileImageService.deleteImage(dto);
        return ResponseEntity.ok("Body image deleted successfully.");
    }
}