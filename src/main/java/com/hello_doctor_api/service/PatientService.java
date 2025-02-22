package com.hello_doctor_api.service;

import com.hello_doctor_api.dto.request.CreatePatientRequest;
import com.hello_doctor_api.dto.request.LoginRequest;
import com.hello_doctor_api.dto.request.OtpRequest;
import com.hello_doctor_api.dto.response.LoginResponse;
import com.hello_doctor_api.dto.response.PatientResponse;
import com.hello_doctor_api.dto.response.VerifyResponse;
import com.hello_doctor_api.entity.Patient;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface PatientService {

  VerifyResponse  getPatientbyEmail(String email);

    VerifyResponse initiateLogin(LoginRequest loginRequest) throws MessagingException, UnsupportedEncodingException;
    LoginResponse verifyOtp(OtpRequest otpRequest);

    ResponseEntity<PatientResponse> createPatient(CreatePatientRequest createPatientRequest, MultipartFile file) throws IOException;
}
