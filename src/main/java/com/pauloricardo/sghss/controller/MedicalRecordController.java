package com.pauloricardo.sghss.controller;

import com.pauloricardo.sghss.entity.MedicalRecord;
import com.pauloricardo.sghss.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService service;

    @GetMapping
    public List<MedicalRecord> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> get(@PathVariable UUID id) {
        MedicalRecord r = service.findById(id);
        return r == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> create(@RequestBody Map<String, String> body) {
        UUID patientId = UUID.fromString(body.get("patientId"));
        UUID authorId = UUID.fromString(body.get("authorId"));
        String note = body.get("note");
        MedicalRecord mr = service.create(patientId, authorId, note);
        return ResponseEntity.status(201).body(mr);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> update(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        String note = body.get("note");
        return ResponseEntity.ok(service.update(id, note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
