package com.hello_doctor_api.service.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hello_doctor_api.dto.request.LoginRequest;
import com.hello_doctor_api.entity.Patient;
import com.hello_doctor_api.repository.PatientRepo;
import com.hello_doctor_api.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class PatientServiceImpl implements PatientService {
   @Autowired
    private PatientRepo patientRepo;

   @Autowired
   private Cloudinary cloudinary;
   @Autowired
   private BCryptPasswordEncoder passwordEncoderDecoder;


    @Override
    public Patient setData(Patient patient, MultipartFile file) throws IOException {
        byte[] image = file.getBytes();
        Map<String, Object> uploadResult = cloudinary.uploader().upload(image, ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");
        patient.setProfilePhoto(imageUrl);
      String newpassword =   passwordEncoderDecoder.encode(patient.getPassword());
        System.out.println("new"+ newpassword);
        patientRepo.save(patient);
        return null;
    }

    @Override
    public Patient byEmail(String email) {
     Patient patient=   this.patientRepo.findByEmail(email);
   //  patient.getProfilePhoto()
        return patient ;
    }

    @Override
    public LoginRequest Login(String email, String password) {
        return null;
    }
}
