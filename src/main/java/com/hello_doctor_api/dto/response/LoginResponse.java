package com.hello_doctor_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String firstName;
    private String fatherName;
    private String LastName;
    private String address1;
    private String address2;
    private String landMark;
    private String city;
    private String state;
    private String zip;
    private String profilePhoto;
}
