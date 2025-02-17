package com.hello_doctor_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {

    private String firstName;
    private String fatherName;
    private String LastName;
    private String email;
    private String profilePhoto;

}
