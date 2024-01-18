package com.example.fitconnect.auth.service;

import com.example.fitconnect.domain.user.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final Environment env;

    public JwtService(Environment env) {
        this.env = env;
    }

    public String generateAccessToken(User user) {
        long expiration = 3600000;
        return generateToken(user.getId(), expiration);
    }

    public String generateRefreshToken(User user) {
        long refreshExpiration = 604800000;
        return generateToken(user.getId(), refreshExpiration);
    }

    private String generateToken(Long userId, long expirationTime) {
        String secretKey = "asdfasdfsdafasdfasdfasdfasdgkasdlhgahahsdfasdsdal";
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}