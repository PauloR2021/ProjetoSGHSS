package com.pauloricardo.sghss.controller;

import com.pauloricardo.sghss.entity.Prescription;
import com.pauloricardo.sghss.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService service;

    @GetMapping
    public List<Prescription> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> get(@PathVariable UUID id) {
        Prescription p = service.findById(id);
        return p == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(p);
    }

    @PostMapping
    public ResponseEntity<Prescription> create(@RequestBody Map<String, String> body) {
        UUID appointmentId = UUID.fromString(body.get("appointmentId"));
        UUID authorId = UUID.fromString(body.get("authorId"));
        String content = body.get("content");
        Prescription p = service.create(appointmentId, authorId, content);
        return ResponseEntity.status(201).body(p);
    }

    @PostMapping("/{id}/sign")
    public ResponseEntity<Prescription> sign(@PathVariable UUID id) {
        return ResponseEntity.ok(service.sign(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
