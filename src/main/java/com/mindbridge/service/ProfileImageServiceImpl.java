package com.mindbridge.service;

import com.mindbridge.dto.ResponseDTO.ProfileImageMainResponseDto;
import com.mindbridge.entity.ProfileImageEntity;
import com.mindbridge.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileImageServiceImpl implements ProfileImageService{
    private final ProfileImageRepository profileImageRepository;

    @Override
    public ProfileImageMainResponseDto getMainProfileImage(long userId) {
        ProfileImageEntity entity = profileImageRepository.findFirstUrlByUserIdOrderByRecordedAtDesc(userId);
        if (entity == null) {
            return new ProfileImageMainResponseDto("");
        }
        String imageUrl = entity.getUrl();
        return new ProfileImageMainResponseDto(imageUrl);
    }

    @Override
    public List<ProfileImageEntity> getProfileImages(long userId) {

        return profileImageRepository.findByUserIdOrderByRecordedAtDesc(userId);
    }
}
