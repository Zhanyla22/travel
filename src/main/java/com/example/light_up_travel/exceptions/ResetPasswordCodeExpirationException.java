package com.example.light_up_travel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResetPasswordCodeExpirationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResetPasswordCodeExpirationException(String message) {
        super(message);
    }
}
