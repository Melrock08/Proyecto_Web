package com.melrock.proyecto_web.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;

@Component
public class JwtUtil {

    // Recomiendo setear un secret >= 256 bits (32 bytes) y preferible en base64 o en vault
    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-ms:3600000}")
    private long expirationMs;

    // -----------------------------
    // GENERAR TOKEN CON EL CORREO
    // -----------------------------
    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    // -----------------------------
    // EXTRAER SUBJECT (correo)
    // -----------------------------
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // -----------------------------
    // PARSEAR TOKEN 
    // -----------------------------
   private Claims extractAllClaims(String token) {
    try {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    } catch (JwtException ex) {
        // Re-lanzar para que el filtro / entry-point lo capture y responda 401
        throw ex;
    }
}

    public boolean isTokenValid(String token, String correo) {
        try {
            String username = extractUsername(token);
            return username != null
                    && username.equalsIgnoreCase(correo)
                    && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date exp = extractExpiration(token);
        return exp.before(new Date());
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

