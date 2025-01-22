package com.hello_doctor_api.repository;

import com.hello_doctor_api.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {
    Patient findByEmail(String email);

}
