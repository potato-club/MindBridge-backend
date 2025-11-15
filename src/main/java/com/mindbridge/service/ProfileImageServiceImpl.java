package com.mindbridge.service;

import com.mindbridge.dto.RequestDto.ProfileImageRequestDto;
import com.mindbridge.dto.ResponseDto.ProfileImageResponseDto;
import com.mindbridge.entity.ProfileImageEntity;
import com.mindbridge.repository.ProfileImageRepository;
import com.mindbridge.validator.MediaValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileImageServiceImpl implements ProfileImageService{
    private final ProfileImageRepository profileImageRepository;
    private final S3Service s3Service;
    private final MediaValidator mediaValidator;

    @Override
    public ProfileImageResponseDto getMainProfileImage(long userId) {
        ProfileImageEntity entity =
                profileImageRepository.findFirstUrlByUserIdOrderByRecordedAtDesc(userId);

        String url = (entity == null)
                ? "https://mindbridge-bucket.s3.ap-northeast-2.amazonaws.com/default_profile.png"
                : entity.getUrl();

        return new ProfileImageResponseDto(url);
    }

    @Override
    public List<ProfileImageEntity> getProfileImages(long userId) {
        return profileImageRepository.findByUserIdOrderByRecordedAtDesc(userId);
    }

    @Override
    public String uploadProfileImage(MultipartFile file, long userId) {

        mediaValidator.validateMedia(file);

        String url = s3Service.uploadImage(file, "profile/");

        profileImageRepository.save(ProfileImageEntity.builder()
                .userId(userId)
                .url(url)
                .build());

        return url;
    }

    @Override
    public void deleteImage(ProfileImageRequestDto profileImageRequestDto) {

        String imageUrl = profileImageRequestDto.imageUrl();

        s3Service.deleteImageFromS3(imageUrl);
        profileImageRepository.deleteByUrl(imageUrl);
    }
}