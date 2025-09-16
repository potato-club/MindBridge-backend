package com.mindbridge.error.customExceptions;

import com.mindbridge.error.ErrorCode;
import lombok.Getter;

@Getter
public class EmptyFileException extends RuntimeException {
    private final ErrorCode errorCode;

    public EmptyFileException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
