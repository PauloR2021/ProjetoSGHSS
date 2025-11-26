package com.pauloricardo.sghss.service;

import com.pauloricardo.sghss.entity.Professional;
import com.pauloricardo.sghss.repository.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfessionalService {

    private final ProfessionalRepository repo;

    public List<Professional> findAll() {
        return repo.findAll();
    }

    public Optional<Professional> findById(UUID id) {
        return repo.findById(id);
    }

    @Transactional
    public Professional create(Professional professional) {
        // poderia adicionar validações (crm único)
        return repo.save(professional);
    }

    @Transactional
    public Professional update(UUID id, Professional updated) {
        Professional exist = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));
        exist.setName(updated.getName());
        exist.setSpecialty(updated.getSpecialty());
        exist.setCrm(updated.getCrm());
        exist.setEmail(updated.getEmail());
        return repo.save(exist);
    }

    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
