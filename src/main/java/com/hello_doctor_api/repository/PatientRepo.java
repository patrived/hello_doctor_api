package com.hello_doctor_api.repository;

import com.hello_doctor_api.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {
   Optional <Patient> findByEmailAndPassword(String email,String password);
    Optional<Patient> findByEmail(String email);

}
