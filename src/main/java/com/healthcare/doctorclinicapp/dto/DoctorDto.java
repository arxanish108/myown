package com.healthcare.doctorclinicapp.dto;

import com.healthcare.doctorclinicapp.entity.Clinic;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Specialization is mandatory")
    @Size(min = 2, max = 50, message = "Specialization must be between 2 and 50 characters")
    private String specialization;

    @Positive(message = "experience must be positive")
    private Integer experience;

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    private List<Clinic> clinicList;
}
