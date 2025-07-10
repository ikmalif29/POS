package com.example.pos.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.pos.models.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {
    private final String SECRETKEY = "PosSecretKey@2024!StaySecureAndFast1234";

    private final Long expiresToken = 1000L * 60 * 30L;

    private final String header = "Authorization";
    private final String prefix = "Bearer ";

    private final JwtParser jwtParser = Jwts.parserBuilder()
            .setSigningKey(SECRETKEY.getBytes(StandardCharsets.UTF_8))
            .build();

    public String generateToken(Users user) {
        Key key = Keys.hmacShaKeyFor(SECRETKEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiresToken))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String resloveToken(HttpServletRequest request) {
        String barearToken = request.getHeader(header);
        if (barearToken != null && barearToken.startsWith(prefix)) {
            return barearToken.substring(prefix.length());
        }
        return null;
    }

    public String resloveClaim(HttpServletRequest request) {
        try {
            String token = resloveToken(request);
            if (token != null) {
                return jwtParser.parseClaimsJws(token)
                        .getBody()
                        .getSubject();
            }
            return null;
        } catch (ExpiredJwtException e) {
            request.setAttribute("expired", e.getMessage());
            throw e;
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            throw e;
        }
    }

    public Boolean validateClaim(Claims claims) {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getAllClaims(String token) {
        return jwtParser.parseClaimsJws(token)
                .getBody();
    }
}
