package com.mindbridge.service;

import com.mindbridge.dto.RequestDto.ProfileImageRequestDto;
import com.mindbridge.dto.ResponseDto.ProfileImageResponseDto;
import com.mindbridge.entity.ProfileImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileImageService {
    ProfileImageResponseDto getMainProfileImage(long userId);
    List<ProfileImageEntity> getProfileImages(long userId);
    String uploadProfileImage(MultipartFile file, long userId);
    void deleteImage(ProfileImageRequestDto profileImageRequestDto);
}