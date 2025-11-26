package com.pauloricardo.sghss.service;

import com.pauloricardo.sghss.entity.AppUser;
import com.pauloricardo.sghss.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AppUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var authorities = user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new User(user.getUsername(), user.getPasswordHash(), user.isEnabled(), true, true, true, authorities);
    }
}