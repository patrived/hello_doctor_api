package com.hello_doctor_api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${app.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private long expirationMinutes;

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }


    public String generateToken(String email, String password) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("password", password);
        return generateToken(claims, email);
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +
                        TimeUnit.MINUTES.toMillis(expirationMinutes)))
                .setIssuer("demo")
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            String username = getUsername(token);
            // Check if the token is expired
            return !isTokenExpired(token) && username != null;
        } catch (JwtException | IllegalArgumentException e) {
            // Log the exception if necessary
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public String extractEmployeeName(String token) {
        return extractClaim(token, claims -> claims.get("employeeName", String.class));
    }

    public String extractOfficialEmail(String token) {
        return extractClaim(token, claims -> claims.get("officialEmail", String.class));
    }

    public String extractRoleName(String token) {
        return extractClaim(token, claims -> claims.get("roleName", String.class));
    }

    public String extractRoleType(String token) {
        return extractClaim(token, claims -> claims.get("roleType", String.class));
    }

    public String extractEmployeeId(String token) {
        return extractClaim(token, claims -> claims.get("employeeId", String.class));
    }

    public String extractDepartmentId(String token) {
        return extractClaim(token, claims -> claims.get("departmentId", String.class));
    }

    public String extractDepartmentName(String token) {
        return extractClaim(token, claims -> claims.get("departmentName", String.class));
    }
}