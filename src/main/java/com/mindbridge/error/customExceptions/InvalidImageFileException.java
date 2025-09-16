package com.mindbridge.error.customExceptions;

import com.mindbridge.error.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidImageFileException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidImageFileException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
