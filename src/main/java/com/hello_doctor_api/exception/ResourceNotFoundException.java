package com.hello_doctor_api.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
        super();

    }


    public ResourceNotFoundException(String message) {
        super(message);

    }

    public ResourceNotFoundException(String message, HttpStatus httpStatus) {
        super(message);

    }


}