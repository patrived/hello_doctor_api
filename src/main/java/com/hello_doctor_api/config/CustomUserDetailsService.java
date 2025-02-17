package com.hello_doctor_api.config;
import com.hello_doctor_api.entity.Patient;
import com.hello_doctor_api.exception.ResourceNotFoundException;
import com.hello_doctor_api.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PatientRepo patientRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws ResourceNotFoundException {

        Patient patient = patientRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        System.out.println("patient ROLE IS : " + patient.getRole());

        return new org.springframework.security.core.userdetails.User(
                patient.getEmail(),
                patient.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + patient.getRole()))
        );

    }
}