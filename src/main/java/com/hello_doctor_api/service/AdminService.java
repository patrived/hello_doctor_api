package com.hello_doctor_api.service;

import com.hello_doctor_api.dto.request.SpecializationRequestDto;

public interface AdminService {
    SpecializationRequestDto createSpecialization(SpecializationRequestDto specializationRequestDto);
}
