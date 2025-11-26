package com.pauloricardo.sghss.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username, List<String> roles) {

        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    public Claims validate(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (JwtException e) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }

    public String getUsername(String token) {
        return validate(token).getSubject();
    }
}
