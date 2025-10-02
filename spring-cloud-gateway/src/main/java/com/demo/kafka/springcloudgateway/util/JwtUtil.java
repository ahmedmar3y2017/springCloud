package com.demo.kafka.springcloudgateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final Key key;
    private final long jwtExpirationMs;

    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.expiration-ms:3600000}") long jwtExpirationMs) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        this.jwtExpirationMs = jwtExpirationMs;
    }


    public boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (JwtException ex) {
            throw new IllegalArgumentException("Invalid JWT token", ex);
        }
    }
}
