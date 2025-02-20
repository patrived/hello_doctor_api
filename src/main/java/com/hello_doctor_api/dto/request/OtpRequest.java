package com.hello_doctor_api.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;


public record OtpRequest(
        @NotNull(message = "Email cannot be null")
        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "OTP cannot be null")
        @Digits(integer = 6, fraction = 0, message = "OTP must be exactly 6 digits")
        Integer otp
) {
    public static OtpRequest build(OtpRequest otpRequest) {
        return new OtpRequest(
                otpRequest.email,
                otpRequest.otp
        );
    }
}
