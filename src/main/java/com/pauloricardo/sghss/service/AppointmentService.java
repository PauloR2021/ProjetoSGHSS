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

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final PatientRepository patientRepo;
    private final ProfessionalRepository professionalRepo;

    public List<Appointment> findAll() {
        return appointmentRepo.findAll();
    }

    public Appointment findById(UUID id) {
        return appointmentRepo.findById(id).orElse(null);
    }

    @Transactional
    public Appointment schedule(UUID patientId, UUID professionalId, OffsetDateTime dateTime, boolean telemedicine) {

        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

        Professional professional = professionalRepo.findById(professionalId)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));

        Appointment ap = new Appointment();
        ap.setPatient(patient);
        ap.setProfessional(professional);
        ap.setDateTime(dateTime);
        ap.setTelemedicine(telemedicine);
        ap.setCancelled(false);

        return appointmentRepo.save(ap);
    }

    @Transactional
    public Appointment update(UUID id, Appointment updated) {
        Appointment ap = appointmentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        ap.setDateTime(updated.getDateTime());
        ap.setTelemedicine(updated.isTelemedicine());
        ap.setCancelled(updated.isCancelled());

        return appointmentRepo.save(ap);
    }

    @Transactional
    public void cancel(UUID id) {
        Appointment ap = appointmentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        ap.setCancelled(true);
        appointmentRepo.save(ap);
    }

    @Transactional
    public void delete(UUID id) {
        appointmentRepo.deleteById(id);
    }
}
