package com.pauloricardo.sghss.controller;

import com.pauloricardo.sghss.entity.Professional;
import com.pauloricardo.sghss.service.ProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
public class ProfessionalController {

    private final ProfessionalService service;

    @GetMapping
    public List<Professional> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professional> get(@PathVariable UUID id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Professional> create(@RequestBody Professional p) {
        return ResponseEntity.status(201).body(service.create(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professional> update(@PathVariable UUID id, @RequestBody Professional p) {
        return ResponseEntity.ok(service.update(id, p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
