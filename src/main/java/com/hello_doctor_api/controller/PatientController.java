package com.hello_doctor_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello_doctor_api.constants.PatientEnum;
import com.hello_doctor_api.dto.request.CreatePatientRequest;
import com.hello_doctor_api.dto.request.LoginRequest;
import com.hello_doctor_api.dto.response.LoginResponse;
import com.hello_doctor_api.dto.response.PatientResponse;
import com.hello_doctor_api.entity.Patient;
import com.hello_doctor_api.exception.PatientCreationException;
import com.hello_doctor_api.exception.ResourceNotFoundException;
import com.hello_doctor_api.service.PatientService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/Patient")
public class PatientController {
    //tt
    @Autowired
    private PatientService patientService;




    @PostMapping(value = "/registerPatient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseEntity<PatientResponse>> createPatientRequest(
            @RequestPart("patient") String patientJson,
            @RequestPart("file") MultipartFile file) {
        System.out.println("patientJson is => " + patientJson);
        System.out.println("file.getName() ---- "+ file.getOriginalFilename());
        System.out.println("ENTERED! ");
        if(file.isEmpty()){
            throw new ResourceNotFoundException(PatientEnum.PE004.getMessgae(),HttpStatus.FORBIDDEN);
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("OBJ");
            CreatePatientRequest createPatientRequest = objectMapper.readValue(patientJson, CreatePatientRequest.class);
            System.out.println("DONE ___ = "+ createPatientRequest.toString());
            ResponseEntity<PatientResponse> createdPatient = this.patientService.createPatient(createPatientRequest, file);
            if (createdPatient != null) {
                return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
            } else {

                throw new PatientCreationException(PatientEnum.PE002.getMessgae());
            }
        } catch (IOException e) {
            throw new PatientCreationException(PatientEnum.PE003.getMessgae(), e);
        }
    }

    @GetMapping("/byemail/{email}")
    public  Patient byEmail(@PathVariable String email){
    return this.patientService.byEmail(email);
    }


@PostMapping("/login")
    public ResponseEntity<String> initiateLogin(@RequestBody LoginRequest loginRequest) throws MessagingException, UnsupportedEncodingException {
        return new ResponseEntity<>(this.patientService.initiateLogin(loginRequest), HttpStatus.OK);
    }

}
