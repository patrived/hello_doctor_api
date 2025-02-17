package com.hello_doctor_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor extends BaseEntity {
    private String doctorFirstName;
    private String doctorLastName;
   // Removed redundant Specialization entity reference
    private String yearsOfExperience;
    private String licenseNumber;
    private String email;
    private String password;
    private String mobile;
    private String alternateNumber;
    private String address;
    private String landMark;
    private String city;
    private String state;
    private String zip;
    private String profilePhoto;
    private String role;

    @ManyToMany(mappedBy = "doctors")
    private List<Patient> patients;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialization_id", referencedColumnName = "id")
    private Specialization specialization;
}
