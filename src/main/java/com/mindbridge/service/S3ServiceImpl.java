package com.mindbridge.service;

import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.EmptyFileException;
import com.mindbridge.error.customExceptions.S3UploadFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service{

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Override
    public String uploadImage(MultipartFile image, String folder) {
        validateImageFileExtension(image.getOriginalFilename());

        try {
            return uploadImageToS3(image, folder);
        } catch (IOException e) {
            throw new EmptyFileException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    private void validateImageFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new EmptyFileException(ErrorCode.MISSING_FILE_EXTENSION);
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extension)) {
            throw new EmptyFileException(ErrorCode.INVALID_FILE_EXTENSION);
        }
    }

    private String uploadImageToS3(MultipartFile image, String folder) throws IOException {
        String originalFilename = image.getOriginalFilename(); //원본 파일 명
        String s3FileName = folder + UUID.randomUUID().toString().substring(0, 10) + "_" + originalFilename; //변경된 파일 명

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3FileName)
                .contentType(image.getContentType())
                .build();

        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(image.getBytes()));
        } catch (Exception e) {
            log.error("S3 Upload failed: {}", e.getMessage(), e);
            throw new S3UploadFailException(ErrorCode.S3_UPLOAD_FAILED);
        }

        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + s3FileName;
    }

    @Override
    public void deleteImageFromS3(String imageUrl) {
        String key = getKeyFromImageAddress(imageUrl);
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new EmptyFileException(ErrorCode.S3_DELETE_FAILED);
        }
    }

    private String getKeyFromImageAddress(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            String decodingKey = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        } catch (MalformedURLException e) {
            throw new EmptyFileException(ErrorCode.INVALID_IMAGE_FILE);
        }
    }
}