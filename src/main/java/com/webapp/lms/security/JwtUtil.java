package com.webapp.lms.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    // ✅ read from application.properties; must be >= 32 chars for HS256
    @Value("${app.jwt.secret:change-me-dev-secret-change-me-dev-secret}")
    private String secret;

    // ✅ default 10 hours (same as your old code) if not provided
    @Value("${app.jwt.expiration:36000000}")
    private long expirationMillis;

    private Key key() {
        // 0.11.5 requires a Key, not a raw String
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // ===== Generate token =====
    public String generateToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key(), SignatureAlgorithm.HS256)   // ✅ 0.11.5-compatible
                .compact();
    }

    // ===== Extract username =====
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ===== Generic claim extractor =====
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parse(token).getBody();
        return claimsResolver.apply(claims);
    }

    // ===== Check expiry =====
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ===== Validate token =====
    public boolean validateToken(String token, String username) {
        try {
            return extractUsername(token).equals(username) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Jws<Claims> parse(String token) {
        // ✅ new parserBuilder() API in 0.11.5
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token);
    }
}
