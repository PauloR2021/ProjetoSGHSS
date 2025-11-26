package com.pauloricardo.sghss.service;

import com.pauloricardo.sghss.entity.Appointment;
import com.pauloricardo.sghss.entity.Prescription;
import com.pauloricardo.sghss.entity.Professional;
import com.pauloricardo.sghss.repository.AppointmentRepository;
import com.pauloricardo.sghss.repository.PrescriptionRepository;
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
public class PrescriptionService {

    private final PrescriptionRepository repo;
    private final AppointmentRepository appointmentRepo;
    private final ProfessionalRepository professionalRepo;

    public List<Prescription> findAll() {
        return repo.findAll();
    }

    public Prescription findById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    @Transactional
    public Prescription create(UUID appointmentId, UUID authorId, String content) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
        Professional author = professionalRepo.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));

        Prescription p = new Prescription();
        p.setAppointment(appointment);
        p.setAuthor(author);
        p.setContent(content);
        p.setSigned(false);
        p.setCreatedAt(OffsetDateTime.now());
        return repo.save(p);
    }

    @Transactional
    public Prescription sign(UUID id) {
        Prescription p = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Prescrição não encontrada"));
        p.setSigned(true);
        return repo.save(p);
    }

    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
