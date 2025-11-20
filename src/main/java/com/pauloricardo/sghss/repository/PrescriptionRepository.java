package com.pauloricardo.sghss.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pauloricardo.sghss.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

}
