package com.mindbridge.validator;

import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.EmptyFileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class MediaValidator {
    private final List<String> allowedImageExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");

    public void validateMedia(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new EmptyFileException(ErrorCode.EMPTY_FILE);
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.contains(".")) {
            throw new EmptyFileException(ErrorCode.MISSING_FILE_EXTENSION);
        }

        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();

        if (!allowedImageExtensions.contains(extension)) {
            throw new EmptyFileException(ErrorCode.INVALID_FILE_EXTENSION);
        }
    }
}
