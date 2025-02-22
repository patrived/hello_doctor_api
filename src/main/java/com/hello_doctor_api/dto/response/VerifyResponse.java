package com.hello_doctor_api.dto.response;

import lombok.Builder;

@Builder
public record VerifyResponse(
        String message,
        String email
) {
}
