package com.hello_doctor_api.service;

import com.hello_doctor_api.dto.request.LoginRequest;
import com.hello_doctor_api.entity.Patient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PatientService {
    Patient setData(Patient patient, MultipartFile files) throws IOException;
    Patient byEmail(String email ) ;
    LoginRequest Login(String email,String password);



}
