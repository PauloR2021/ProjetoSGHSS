package com.pauloricardo.sghss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pauloricardo.sghss.entity.Patient;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
}