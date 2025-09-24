package com.mindbridge.service;

import com.mindbridge.dto.ResponseDTO.ProfileImageMainResponseDto;
import com.mindbridge.entity.ProfileImageEntity;

import java.util.List;

public interface ProfileImageService {
    ProfileImageMainResponseDto getMainProfileImage(long userId);
    List<ProfileImageEntity> getProfileImages(long userId);
}
