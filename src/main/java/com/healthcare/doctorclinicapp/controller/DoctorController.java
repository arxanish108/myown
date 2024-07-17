package com.healthcare.doctorclinicapp.controller;

import com.healthcare.doctorclinicapp.dto.DoctorDto;
import com.healthcare.doctorclinicapp.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("doc")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorDto> create(@Valid @RequestBody DoctorDto doctorDto){
        return new ResponseEntity<>(doctorService.create(doctorDto), HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<DoctorDto> get(@PathVariable String email) throws Exception {
        return new ResponseEntity<>(doctorService.get(email),HttpStatus.FOUND);
    }

    @DeleteMapping("/{mail}")
    public ResponseEntity<Void> delete(@PathVariable String mail){
        doctorService.delete(mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<DoctorDto> updateDoctor(@RequestBody DoctorDto doctorDto) throws Exception {
        return new ResponseEntity<>(doctorService.update(doctorDto),HttpStatus.OK);
    }



}
