package com.healthcare.doctorclinicapp.testService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.doctorclinicapp.dto.DoctorDto;
import com.healthcare.doctorclinicapp.entity.Clinic;
import com.healthcare.doctorclinicapp.entity.Doctor;
import com.healthcare.doctorclinicapp.repository.DoctorRepository;
import com.healthcare.doctorclinicapp.service.DoctorService;
import com.healthcare.doctorclinicapp.service.DoctorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Autowired
    private DoctorDto doctorDto;
    @Autowired
    private Doctor doctor;

    @Autowired
    private Clinic clinic;

    @BeforeEach
    public void setup() {
        // Initialize the Clinic list
        List<Clinic> clinicList = new ArrayList<>();
        Clinic clinic = Clinic.builder()
                .id(1)
                .name("Clinic A")
                .address("123 Main St")
                .specialization("General Medicine")
                .build();
        clinicList.add(clinic);

        // Initialize the DoctorDto object
        doctorDto = DoctorDto.builder()
                .id(1)
                .name("John Doe")
                .email("johndoe@example.com")
                .specialization("Cardiology")
                .experience(10)
                .clinicList(clinicList)
                .build();

        // Initialize the Doctor entity object
        doctor = Doctor.builder()
                .id(1)
                .name("John Doe")
                .email("johndoe@example.com")
                .specialization("Cardiology")
                .experience(10)
                .clinicList(clinicList)
                .build();

        // Set the doctor reference in the clinic
        //clinic.setDoctor(doctor);
    }


    @Test
    void test_create(){

        when(doctorRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(doctor));
        when(objectMapper.convertValue(doctorDto,Doctor.class)).thenReturn(doctor);
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(objectMapper.convertValue(doctor,DoctorDto.class)).thenReturn(doctorDto);

        DoctorDto doctorDto2 = doctorService.create(doctorDto);
        verify(doctorRepository,times(1)).save(doctor);
    }

    @Test
    void get_doctor() throws Exception {
        when(doctorRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(doctor));
        when(objectMapper.convertValue(doctor,DoctorDto.class)).thenReturn(doctorDto);

        DoctorDto doctorDto1 = doctorService.get(ArgumentMatchers.anyString());

        verify(doctorRepository,times(1)).findByEmail(ArgumentMatchers.anyString());
    }

    @Test
    void delete_doctor(){
        when(doctorRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(doctor));
        doNothing().when(doctorRepository).delete(doctor);

        doctorService.delete(ArgumentMatchers.anyString());

        verify(doctorRepository,times(1)).delete(doctor);

    }

    @Test
    void update_doc() throws Exception {
        when(doctorRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(doctor));
        when(objectMapper.convertValue(doctorDto,Doctor.class)).thenReturn(doctor);
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(objectMapper.convertValue(doctor,DoctorDto.class)).thenReturn(doctorDto);

        DoctorDto doctorDto1 = doctorService.update(doctorDto);

        verify(doctorRepository,times(1)).save(doctor);
    }

}
