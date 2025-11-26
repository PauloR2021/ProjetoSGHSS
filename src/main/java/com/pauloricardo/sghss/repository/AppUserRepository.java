package com.pauloricardo.sghss.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pauloricardo.sghss.entity.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByUsername(String username);
}
