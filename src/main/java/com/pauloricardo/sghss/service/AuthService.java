package com.pauloricardo.sghss.service;

import com.pauloricardo.sghss.entity.AppUser;
import com.pauloricardo.sghss.repository.AppUserRepository;
import com.pauloricardo.sghss.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public String registerAndGetToken(String username, String rawPassword, Set<String> roles) {

        if (userRepo.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Usuário já existe");
        }

        AppUser u = new AppUser();
        u.setUsername(username);
        u.setPasswordHash(passwordEncoder.encode(rawPassword));
        u.setRoles(roles);
        u.setEnabled(true);

        userRepo.save(u);

        // JwtUtil aceita List<String>
        return jwtUtil.generateToken(username, roles.stream().toList());
    }

    public String authenticateAndGetToken(String username, String rawPassword) {

        AppUser u = userRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(rawPassword, u.getPasswordHash())) {
            throw new IllegalArgumentException("Usuário ou senha inválidos");
        }

        return jwtUtil.generateToken(
                username,
                u.getRoles().stream().toList());
    }
}
