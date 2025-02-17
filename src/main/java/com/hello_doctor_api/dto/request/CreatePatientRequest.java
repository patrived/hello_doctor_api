package com.hello_doctor_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePatientRequest {
    private String firstName;
    private String fatherName;
    private String lastName;
    private String address;
    private String address2;
    private String landmark;
    private String country;
    private String city;
    private String state;
    private String zip;
    private String mobile;
    private String alternateNumber;
    private String email;
    private String password;
    private String profilephoto;
//    private String role;
}
