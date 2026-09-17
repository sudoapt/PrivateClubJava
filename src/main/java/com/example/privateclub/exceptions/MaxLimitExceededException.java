package com.example.privateclub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
public class MaxLimitExceededException extends RuntimeException {
    public MaxLimitExceededException(String message) {
        super(message);
    }
}
