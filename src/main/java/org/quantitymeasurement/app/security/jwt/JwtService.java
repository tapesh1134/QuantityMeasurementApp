package org.quantitymeasurement.app.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.quantitymeasurement.app.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${spring.application.key}")
    private String key;

    @Value("${spring.application.token.expiry}")
    private long tokenExpiry;

    public String generateToken(User users){
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", users.getId().toString());
        return Jwts.builder().claims().add(claims).subject(users.getId().toString()).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis()+tokenExpiry*1000)).and().signWith(getKey()).compact();
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> Long.valueOf(claims.get("sub").toString()));
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private Key getKey() {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }
}