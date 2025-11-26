package com.pauloricardo.sghss.controller;

import com.pauloricardo.sghss.entity.Patient;
import com.pauloricardo.sghss.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Patient> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> get(@PathVariable UUID id) {
        Patient p = service.findById(id);
        return p == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(p);
    }

    @PostMapping
    public ResponseEntity<Patient> create(@RequestBody Patient p) {
        Patient saved = service.save(p);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable UUID id, @RequestBody Patient p) {
        Patient exist = service.findById(id);
        if (exist == null)
            return ResponseEntity.notFound().build();
        exist.setName(p.getName());
        exist.setCpf(p.getCpf());
        exist.setEmail(p.getEmail());
        exist.setPhone(p.getPhone());
        return ResponseEntity.ok(service.save(exist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}