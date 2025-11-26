package com.mindbridge.service;

import com.mindbridge.dto.ResponseDto.ProfileImageResponseDto;
import com.mindbridge.entity.ProfileImageEntity;
import com.mindbridge.entity.UserEntity;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.InvalidImageFileException;
import com.mindbridge.error.customExceptions.UserNotFoundException;
import com.mindbridge.repository.ProfileImageRepository;
import com.mindbridge.repository.UserRepository;
import com.mindbridge.validator.MediaValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileImageServiceImpl implements ProfileImageService{
    private final ProfileImageRepository profileImageRepository;
    private final S3Service s3Service;
    private final MediaValidator mediaValidator;
    private final UserRepository userRepository;

    @Value("${app.default-profile-image}")
    private String defaultProfileImageUrl;

    @Override
    public ProfileImageResponseDto getMainProfileImage(long userId) {
        ProfileImageEntity entity =
                profileImageRepository.findFirstUrlByUserIdOrderByRecordedAtDesc(userId);

        String url = (entity == null)
                ? defaultProfileImageUrl
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

        Optional<ProfileImageEntity> existing = profileImageRepository.findByUserId(userId);

        ProfileImageEntity saved;

        if(existing.isPresent()) {
            ProfileImageEntity entity = existing.get();
            entity.setUrl(url);
            saved = profileImageRepository.save(entity);
        } else {
            saved = profileImageRepository.save(
                    ProfileImageEntity.builder()
                            .userId(userId)
                            .url(url)
                            .build()
            );
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        user.setProfileImageId(saved.getId());
        userRepository.save(user);

        return url;
    }

    @Override
    public void deleteImage(long imageId, long currentUserId) {
        ProfileImageEntity image = profileImageRepository.findById(imageId)
                        .orElseThrow(() -> new InvalidImageFileException(ErrorCode.EMPTY_FILE));

        if (!image.getUserId().equals(currentUserId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        s3Service.deleteImageFromS3(image.getUrl());
        profileImageRepository.deleteById(image.getId());
    }
}