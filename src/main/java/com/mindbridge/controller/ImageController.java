package com.mindbridge.controller;

import com.mindbridge.dto.RequestDTO.ProfileImageDeleteRequestDto;
import com.mindbridge.dto.ResponseDTO.ProfileImageMainResponseDto;
import com.mindbridge.dto.ResponseDTO.ProfileImageUploadResponseDto;
import com.mindbridge.entity.ProfileImageEntity;
import com.mindbridge.service.ProfileImageService;
import com.mindbridge.service.S3ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/body-image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ProfileImageService profileImageService;
    private final S3ImageService s3ImageService;

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileImageMainResponseDto> getMainProfilePhoto(@PathVariable Long userId) {
        return ResponseEntity.ok(profileImageService.getMainProfileImage(userId));
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<ProfileImageEntity>> getProfileImages(@PathVariable Long userId) {

        List<ProfileImageEntity> profileImageEntityList = profileImageService.getProfileImages(userId);
        return ResponseEntity.ok(profileImageEntityList);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ProfileImageUploadResponseDto> uploadProfileImage(@PathVariable Long userId,
                                                                            @RequestPart(value = "image", required = false) MultipartFile image) {
        String url = s3ImageService.upload(image, userId);

        return ResponseEntity.ok(new ProfileImageUploadResponseDto(url));
    }

    @DeleteMapping
    public ResponseEntity<?> s3delete(@RequestBody ProfileImageDeleteRequestDto dto) {
        s3ImageService.deleteImageFromS3(dto);
        return ResponseEntity.ok("Body image deleted successfully.");
    }
}
