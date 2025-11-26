package com.pauloricardo.sghss.service;

import com.pauloricardo.sghss.entity.AppUser;
import com.pauloricardo.sghss.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppUserService {

    private final AppUserRepository repo;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<AppUser> findById(UUID id) {
        return repo.findById(id);
    }

    public Optional<AppUser> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Transactional
    public AppUser create(String username, String rawPassword, Set<String> roles) {
        if (repo.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Usuário já existe");
        }
        AppUser u = new AppUser();
        u.setUsername(username);
        u.setPasswordHash(passwordEncoder.encode(rawPassword));
        u.setRoles(roles);
        u.setEnabled(true);
        return repo.save(u);
    }

    @Transactional
    public void changePassword(UUID userId, String newPassword) {
        AppUser u = repo.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        u.setPasswordHash(passwordEncoder.encode(newPassword));
        repo.save(u);
    }

    @Transactional
    public void updateRoles(UUID userId, Set<String> roles) {
        AppUser u = repo.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        u.setRoles(roles);
        repo.save(u);
    }

    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
