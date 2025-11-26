package com.pauloricardo.sghss.service;

import com.pauloricardo.sghss.entity.MedicalRecord;
import com.pauloricardo.sghss.entity.Patient;
import com.pauloricardo.sghss.entity.Professional;
import com.pauloricardo.sghss.repository.MedicalRecordRepository;
import com.pauloricardo.sghss.repository.PatientRepository;
import com.pauloricardo.sghss.repository.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicalRecordService {

    private final MedicalRecordRepository repo;
    private final PatientRepository patientRepo;
    private final ProfessionalRepository professionalRepo;

    public List<MedicalRecord> findAll() {
        return repo.findAll();
    }

    public MedicalRecord findById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    @Transactional
    public MedicalRecord create(UUID patientId, UUID authorId, String note) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
        Professional author = professionalRepo.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));

        MedicalRecord mr = new MedicalRecord();
        mr.setPatient(patient);
        mr.setAuthor(author);
        mr.setNote(note);
        mr.setCreatedAt(OffsetDateTime.now());
        return repo.save(mr);
    }

    @Transactional
    public MedicalRecord update(UUID id, String note) {
        MedicalRecord exist = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prontuário não encontrado"));
        exist.setNote(note);
        return repo.save(exist);
    }

    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
