package com.mindbridge.service;

import com.mindbridge.dto.RequestDTO.ProfileImageDeleteRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface S3ImageService {
    String upload(MultipartFile image, long userId);
    void deleteImageFromS3(ProfileImageDeleteRequestDto imageUrl);
    String uploadImage(MultipartFile image, String folder);
}
