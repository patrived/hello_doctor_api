package com.hello_doctor_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    private String firstName;
    private String fatherName;
    private String LastName;
    private String address1;
    private String address2;
    private String landMark;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String password;
    private String profilePhoto;





}
