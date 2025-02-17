package com.hello_doctor_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Specialization extends BaseEntity {
    private String specialization;

    @OneToOne(mappedBy = "specialization")
    private Doctor doctor;
}
