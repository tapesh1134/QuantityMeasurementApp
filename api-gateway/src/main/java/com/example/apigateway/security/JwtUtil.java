package com.example.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {

    @Value("${spring.application.key}")
    private String key;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public Claims validateAndExtractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())   // ✅ SAME as auth service
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}