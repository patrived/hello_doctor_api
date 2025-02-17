package com.hello_doctor_api.service;

import com.hello_doctor_api.dto.request.CreatePatientRequest;
import com.hello_doctor_api.dto.request.LoginRequest;
import com.hello_doctor_api.dto.response.LoginResponse;
import com.hello_doctor_api.dto.response.PatientResponse;
import com.hello_doctor_api.entity.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PatientService {

   Patient byEmail(String email ) ;
    LoginResponse Login(LoginRequest loginRequest);


    ResponseEntity<PatientResponse> createPatient(CreatePatientRequest createPatientRequest, MultipartFile file) throws IOException;
}
