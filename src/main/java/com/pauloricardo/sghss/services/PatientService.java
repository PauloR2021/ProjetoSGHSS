package com.pauloricardo.sghss.services;

import com.pauloricardo.sghss.entity.Patient;
import com.pauloricardo.sghss.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public List<Patient> findAll() {
        return repo.findAll();
    }

    public Patient findById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public Patient save(Patient p) {
        return repo.save(p);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}