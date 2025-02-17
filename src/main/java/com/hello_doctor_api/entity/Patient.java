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
public class Patient extends BaseEntity {

    private String firstName;
    private String fatherName;
    private String lastName;
    private String address1;
    private String address2;
    private String landMark;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String password;
    private String mobile;
    private String alternateNumber;
    private String profilePhoto;
    private String role;

    @ManyToMany
    @JoinTable(
            name = "patient_doctor",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<Doctor> doctors;
}
