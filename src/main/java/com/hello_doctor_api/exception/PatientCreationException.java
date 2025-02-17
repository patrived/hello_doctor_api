package com.hello_doctor_api.exception;


public class PatientCreationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PatientCreationException(String message) {
        super(message);
    }

    public PatientCreationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}