package com.pauloricardo.sghss.controller;

import com.pauloricardo.sghss.entity.Appointment;
import com.pauloricardo.sghss.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping
    public List<Appointment> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> get(@PathVariable UUID id) {
        Appointment a = service.findById(id);
        return a == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(a);
    }

    @PostMapping
    public ResponseEntity<Appointment> schedule(@RequestBody Map<String, String> body) {
        UUID patientId = UUID.fromString(body.get("patientId"));
        UUID professionalId = UUID.fromString(body.get("professionalId"));
        OffsetDateTime dateTime = OffsetDateTime.parse(body.get("dateTime"));
        boolean tele = Boolean.parseBoolean(body.getOrDefault("telemedicine", "false"));
        Appointment ap = service.schedule(patientId, professionalId, dateTime, tele);
        return ResponseEntity.status(201).body(ap);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(@PathVariable UUID id, @RequestBody Appointment a) {
        return ResponseEntity.ok(service.update(id, a));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        service.cancel(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
