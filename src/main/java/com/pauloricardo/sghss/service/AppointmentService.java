package com.pauloricardo.sghss.service;

import com.pauloricardo.sghss.entity.Appointment;
import com.pauloricardo.sghss.entity.Patient;
import com.pauloricardo.sghss.entity.Professional;
import com.pauloricardo.sghss.repository.AppointmentRepository;
import com.pauloricardo.sghss.repository.PatientRepository;
import com.pauloricardo.sghss.repository.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentService {

    private final AppointmentRepository repo;
    private final PatientRepository patientRepo;
    private final ProfessionalRepository professionalRepo;

    public List<Appointment> findAll() {
        return repo.findAll();
    }

    public Appointment findById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    /**
     * Agenda uma consulta. Faz validações simples:
     * - paciente e profissional existem
     * - não há outra consulta do mesmo profissional no mesmo horário (checagem
     * simples)
     */
    @Transactional
    public Appointment schedule(UUID patientId, UUID professionalId, LocalDateTime dateTime, boolean telemedicine) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
        Professional professional = professionalRepo.findById(professionalId)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));

        // Checagem simples de conflito (mesmo profissional no mesmo horário)
        boolean conflict = repo.findAll().stream()
                .anyMatch(a -> a.getProfessional() != null
                        && a.getProfessional().getId().equals(professionalId)
                        && a.getDateTime() != null
                        && a.getDateTime().equals(dateTime)
                        && !"CANCELLED".equalsIgnoreCase(a.getStatus()));

        if (conflict) {
            throw new IllegalStateException("Profissional já tem consulta neste horário");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setProfessional(professional);
        appointment.setDateTime(dateTime);
        appointment.setStatus("SCHEDULED");
        appointment.setTelemedicine(telemedicine);
        return repo.save(appointment);
    }

    @Transactional
    public Appointment update(UUID id, Appointment updated) {
        Appointment exist = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
        exist.setDateTime(updated.getDateTime());
        exist.setStatus(updated.getStatus());
        exist.setTelemedicine(updated.isTelemedicine());
        return repo.save(exist);
    }

    @Transactional
    public void cancel(UUID id) {
        Appointment exist = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
        exist.setStatus("CANCELLED");
        repo.save(exist);
    }

    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
