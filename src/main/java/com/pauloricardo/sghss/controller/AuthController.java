package com.pauloricardo.sghss.controller;

import com.pauloricardo.sghss.entity.AppUser;
import com.pauloricardo.sghss.repository.AppUserRepository;
import com.pauloricardo.sghss.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (userRepo.findByUsername(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe");
        }
        AppUser u = new AppUser();
        u.setUsername(username);
        u.setPasswordHash(encoder.encode(password));
        u.setRoles(Set.of("ROLE_USER"));
        userRepo.save(u);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("username", username));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        var opt = userRepo.findByUsername(body.get("username"));
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        AppUser u = opt.get();
        if (!encoder.matches(body.get("password"), u.getPasswordHash()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String token = jwtUtil.generateToken(u.getUsername(), u.getRoles().stream().toList());
        return ResponseEntity.ok(Map.of("token", token));
    }
}