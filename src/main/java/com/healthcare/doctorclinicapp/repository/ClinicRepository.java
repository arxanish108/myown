package com.healthcare.doctorclinicapp.repository;

import com.healthcare.doctorclinicapp.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic,Integer> {
}