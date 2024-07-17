package com.healthcare.doctorclinicapp.service;

import com.healthcare.doctorclinicapp.dto.DoctorDto;

public interface DoctorService {
    DoctorDto create(DoctorDto doctorDto);

    DoctorDto get(String email) throws Exception;

    void delete(String mail);

    DoctorDto update(DoctorDto doctorDto) throws Exception;
}
