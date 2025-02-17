package com.hello_doctor_api.payloads;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Builder
public class ApiResponseMessage<T> {

    private HttpStatus status;
    private Integer code;
    private String message;
    private T data;
    private Map<String, String> errorDetails;

}


