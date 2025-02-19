package com.hello_doctor_api.service.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hello_doctor_api.config.OtpConfig;
import com.hello_doctor_api.constants.PatientEnum;
import com.hello_doctor_api.constants.Role;
import com.hello_doctor_api.dto.request.CreatePatientRequest;
import com.hello_doctor_api.dto.request.LoginRequest;
import com.hello_doctor_api.dto.request.OtpRequest;
import com.hello_doctor_api.dto.response.LoginResponse;
import com.hello_doctor_api.dto.response.PatientResponse;
import com.hello_doctor_api.entity.Patient;
import com.hello_doctor_api.exception.FileEmptyException;
import com.hello_doctor_api.exception.PatientCreationException;
import com.hello_doctor_api.filter.SecurityFilter;
import com.hello_doctor_api.repository.PatientRepo;
import com.hello_doctor_api.service.PatientService;
import com.hello_doctor_api.utils.JwtUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
   @Autowired
    private PatientRepo patientRepo;

   @Autowired
   private Cloudinary cloudinary;
   @Autowired
   private BCryptPasswordEncoder passwordEncoderDecoder;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    OtpConfig  otpConfig;

    @Override
    public ResponseEntity<PatientResponse> createPatient(CreatePatientRequest createPatientRequest, MultipartFile file) throws IOException {
        PatientResponse patientResponse = new PatientResponse();
        try {
            if (file.isEmpty()) {

                throw new FileEmptyException("File is empty. Please provide a valid file.");
            }
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            createPatientRequest.setProfilephoto(imageUrl);
            String newPassword = passwordEncoderDecoder.encode(createPatientRequest.getPassword());
            createPatientRequest.setPassword(newPassword);
            Patient patient = new Patient();
            patient.setFirstName(createPatientRequest.getFirstName());
            patient.setFatherName(createPatientRequest.getFatherName());
            patient.setLastName(createPatientRequest.getLastName());
            patient.setAddress1(createPatientRequest.getAddress1());
            patient.setAddress2(createPatientRequest.getAddress2());
            patient.setLandMark(createPatientRequest.getLandMark());
            patient.setCity(createPatientRequest.getCity());
            patient.setState(createPatientRequest.getState());
            patient.setZip(createPatientRequest.getZip());
            patient.setEmail(createPatientRequest.getEmail());
            patient.setPassword(createPatientRequest.getPassword());
            patient.setProfilePhoto(createPatientRequest.getProfilephoto());
            patient.setRole(Role.R001.getMessage());
            patient.setLastUpdatedDate(new Date());

            // Save the patient entity to the database
            try {
                patientRepo.save(patient);
            } catch (Exception e) {
                // Throw a custom exception if saving fails
                throw new PatientCreationException(PatientEnum.PE002 + e.getMessage());
            }

            patientResponse.setFirstName(patient.getFirstName());
            patientResponse.setLastName(patient.getLastName());
            patientResponse.setEmail(patient.getEmail());
            patientResponse.setProfilePhoto(patient.getProfilePhoto());
           // return new ResponseEntity<>(patientResponse, HttpStatus.CREATED);

        } catch (FileEmptyException | PatientCreationException e) {
        } catch (Exception e) {
            throw new RuntimeException(PatientEnum.E500 + e.getMessage(), e);
        }
        return  new ResponseEntity<>(patientResponse, HttpStatus.CREATED);
    }
    @Override
    public Patient byEmail(String email) {
        Optional<Patient> patient = this.patientRepo.findByEmail(email);
        return patient.orElse(null);
    }


    @Override
    public String  initiateLogin(LoginRequest loginRequest) throws MessagingException, UnsupportedEncodingException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        // Check if the user exists
        Patient patient = patientRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Generate & Send OTP
        otpConfig.generateOtp(loginRequest.getEmail());

        return "OTP has been sent to your email. Please verify to continue.";
    }

    @Override
    public LoginResponse verifyOtp(OtpRequest otpRequest) {
        JavaMailSender  javaMailSender = new JavaMailSenderImpl();
        OtpConfig otpConfig = new OtpConfig();
        boolean isValidOtp = otpConfig.validateOtp(otpRequest.email(), otpRequest.otp());
        if (!isValidOtp) {
            throw new RuntimeException("Invalid or expired OTP");
        }
        Patient patient = patientRepo.findByEmail(otpRequest.email())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        String token = jwtUtil.generateToken(otpRequest.email(), "");
        return LoginResponse.builder()
                .firstName(patient.getFirstName())
                .LastName(patient.getLastName())
                .token(token)
                .build();
    }


}
