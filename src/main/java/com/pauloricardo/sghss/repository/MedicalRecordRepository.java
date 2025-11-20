package com.pauloricardo.sghss.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pauloricardo.sghss.entity.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {
}
