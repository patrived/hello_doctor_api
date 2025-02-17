package com.hello_doctor_api.repository;

import com.hello_doctor_api.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepo extends JpaRepository<Specialization,Long> {
}
