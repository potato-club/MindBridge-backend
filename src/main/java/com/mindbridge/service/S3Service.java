package com.mindbridge.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadImage(MultipartFile image, String folder);
    void deleteImageFromS3(String imageUrl);
}