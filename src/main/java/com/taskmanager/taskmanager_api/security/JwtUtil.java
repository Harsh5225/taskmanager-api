package com.taskmanager.taskmanager_api.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "THIS_IS_A_SECRET_KEY_CHANGE_IT_IN_REAL_PROJECT_1234567890";
    private final long EXPIRATION = 1000 * 60 * 60 * 10; // 10 hours

    // Generates a cryptographic key from the secret.
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

//    Creates a JWT token for the user.
//    subject → email
//    current time
//    expiry
//    signed using HS256 algorithm
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Reads the token and extracts the email.
    //Used when validating requests.

    public String extractEmail(String token) {
        return parseToken(token).getBody().getSubject();
    }



    //Checks:
    //Signature correct?
    //Not expired?
    //Not tampered?
    public boolean isTokenValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }
}
