package com.hello_doctor_api.controller;

import com.hello_doctor_api.dto.request.SpecializationRequestDto;
import com.hello_doctor_api.dto.response.ErrorResponse;
import com.hello_doctor_api.exception.CustomBadRequestException;
import com.hello_doctor_api.exception.CustomInternalServerException;
import com.hello_doctor_api.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/Admin")
public class AdminController {

    private  AdminService adminService;

    // Constructor-based Dependency Injection
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/specialization")
    public ResponseEntity<?> createSpecialization(@RequestBody SpecializationRequestDto specializationRequestDto) {
        try {
            SpecializationRequestDto responseDto = adminService.createSpecialization(specializationRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (CustomBadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (CustomInternalServerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Unexpected error occurred"));
        }
    }
}
