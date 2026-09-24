package com.example.watchlist;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey key = Jwts.SIG.HS256.key().build();

    public String generateToken(String username, Role role) {
        return
                Jwts.builder()
                .subject(username)
                        .claim("role", role.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 часов
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {

        return
        Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();


    }

    public String extractRole(String token) {

        return
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .get("role", String.class);


    }
}
