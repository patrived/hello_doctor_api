package com.hello_doctor_api.service.serviceImpl;

import com.hello_doctor_api.dto.request.SpecializationRequestDto;
import com.hello_doctor_api.entity.Specialization;
import com.hello_doctor_api.exception.CustomBadRequestException;
import com.hello_doctor_api.exception.CustomInternalServerException;
import com.hello_doctor_api.repository.SpecializationRepo;
import com.hello_doctor_api.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
@Autowired
   private SpecializationRepo specializationRepo;

    @Override
    public SpecializationRequestDto createSpecialization(SpecializationRequestDto specializationRequestDto) {
        try {
            // Validate input
            if (specializationRequestDto == null || specializationRequestDto.getSpecialization() == null || specializationRequestDto.getSpecialization().trim().isEmpty()) {
                throw new IllegalArgumentException("Specialization name cannot be null or empty.");
            }

            // Create and save entity
            Specialization specialization = new Specialization();
            specialization.setSpecialization(specializationRequestDto.getSpecialization());
            specialization = specializationRepo.save(specialization);

            // Convert to DTO
            SpecializationRequestDto responseDto = new SpecializationRequestDto();
            responseDto.setSpecialization(specialization.getSpecialization());

            return responseDto;

        } catch (IllegalArgumentException e) {
            throw new CustomBadRequestException("Invalid Specialization data: " + e.getMessage());
        } catch (Exception e) {
            throw new CustomInternalServerException("Error saving specialization: " + e.getMessage());
        }
    }

}
