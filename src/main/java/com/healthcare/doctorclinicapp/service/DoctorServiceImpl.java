package com.healthcare.doctorclinicapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.doctorclinicapp.dto.DoctorDto;
import com.healthcare.doctorclinicapp.entity.Clinic;
import com.healthcare.doctorclinicapp.entity.Doctor;
import com.healthcare.doctorclinicapp.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorServiceImpl implements DoctorService{

    private final ObjectMapper objectMapper;
    private final DoctorRepository doctorRepository;

    @Override
    public DoctorDto create(DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findByEmail(doctorDto.getEmail())
                        .orElse(objectMapper.convertValue(doctorDto, Doctor.class));
        doctor.getClinicList().forEach(clinic -> clinic.setDoctor(doctor));
        doctorRepository.save(doctor);
        return doctorDto;
    }

    @Override
    public DoctorDto get(String email) throws Exception {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Quiz Not Found"));
        return objectMapper.convertValue(doctor,DoctorDto.class);
    }

    @Override
    public void delete(String mail) {
        Doctor doctor = doctorRepository.findByEmail(mail)
                        .orElseThrow(() -> new RuntimeException("notvalid"));
        doctorRepository.delete(doctor);
    }

    @Override
    public DoctorDto update(DoctorDto doctorDto) throws Exception {
        Doctor doctor = objectMapper.convertValue(doctorDto, Doctor.class);
        Doctor existingDoctor = doctorRepository.findById(doctorDto.getId())
                .orElseThrow(() -> new Exception("Doctor not found"));

        // Update the existing doctor's fields with values from the DTO
        existingDoctor.setName(doctorDto.getName());
        existingDoctor.setEmail(doctorDto.getEmail());
        existingDoctor.setSpecialization(doctorDto.getSpecialization());
        existingDoctor.setExperience(doctorDto.getExperience());

        existingDoctor.getClinicList().clear();
        existingDoctor.getClinicList().forEach(clinic -> clinic.setDoctor(existingDoctor));
        //Doctor doctor = objectMapper.convertValue(doctorDto, Doctor.class);
        return objectMapper.convertValue(doctorRepository.save(existingDoctor), DoctorDto.class);
    }
}
