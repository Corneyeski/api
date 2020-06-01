package com.wefox.challenge.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Indicates that a requested resource does not exists.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("The requested resource does not exists.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
