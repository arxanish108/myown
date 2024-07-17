package com.healthcare.doctorclinicapp.testController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.doctorclinicapp.controller.DoctorController;
import com.healthcare.doctorclinicapp.dto.DoctorDto;
import com.healthcare.doctorclinicapp.entity.Doctor;
import com.healthcare.doctorclinicapp.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {
    @MockBean
    private DoctorService doctorService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void meth(){

    }
    @Test
    void test_createDoctor() throws Exception {
        DoctorDto doctorDtoRequest = DoctorDto.builder()
                .name("John Doe")
                .email("johndoe@example.com")
                .specialization("Cardiology")
                .experience(10)
                .build();

        when(doctorService.create(doctorDtoRequest)).thenReturn(doctorDtoRequest);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/doc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctorDtoRequest))
        )
                .andExpect(status().isCreated());

        verify(doctorService,times(1)).create(doctorDtoRequest);
    }

    @Test
    void get_doctor() throws Exception {
        DoctorDto doctorDtoRequest = DoctorDto.builder()
                .name("John Doe")
                .email("johndoe@example.com")
                .specialization("Cardiology")
                .experience(10)
                .build();

        when(doctorService.get("johndoe@example.com")).thenReturn(doctorDtoRequest);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/doc/{email}","johndoe@example.com")
                .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isFound());

        verify(doctorService,times(1)).get(ArgumentMatchers.anyString());
    }

    @Test
    void delete_doctor() throws Exception {
        DoctorDto doctorDto = DoctorDto.builder()
                .name("John Doe")
                .email("johndoe@example.com")
                .specialization("Cardiology")
                .experience(10)
                .build();

        doNothing().when(doctorService).delete(ArgumentMatchers.anyString());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/doc/{mail}","johndoe@example.com")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        //verify(doctorService,times(1)).delete(ArgumentMatchers.anyString());
    }

    @Test
    void update_doc() throws Exception {
        DoctorDto doctorDto = DoctorDto.builder()
                .name("John Doe")
                .email("johndoe@example.com")
                .specialization("Cardiology")
                .experience(10)
                .build();
        when(doctorService.update(doctorDto)).thenReturn(doctorDto);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/doc",doctorDto)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctorDto))
        )
                .andExpect(status().isOk());

        verify(doctorService,times(1)).update(doctorDto);
    }
}
