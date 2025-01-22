package com.hello_doctor_api.controller;

import com.hello_doctor_api.entity.Patient;
import com.hello_doctor_api.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/set")
    public Patient setData(@RequestPart Patient patient, @RequestParam("file") MultipartFile file) throws IOException {
        return this.patientService.setData(patient,file);
    }
    @GetMapping("/byemail/{email}")
    public  Patient byEmail(@PathVariable String email){
    return this.patientService.byEmail(email);
    }


}
