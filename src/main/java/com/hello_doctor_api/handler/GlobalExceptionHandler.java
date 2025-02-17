package com.hello_doctor_api.handler;

import com.hello_doctor_api.constants.PatientEnum;
import com.hello_doctor_api.dto.response.ErrorResponse;
import com.hello_doctor_api.exception.FileEmptyException;
import com.hello_doctor_api.exception.PatientCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<ErrorResponse> handleFileEmptyException(FileEmptyException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientCreationException.class)
    public ResponseEntity<ErrorResponse> handlePatientCreationException(PatientCreationException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(PatientEnum.E500 + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}