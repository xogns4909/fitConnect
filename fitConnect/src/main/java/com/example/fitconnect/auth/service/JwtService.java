package com.example.fitconnect.auth.service;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.domain.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {


    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.refreshExpiration}")
    private Long refreshExpiration;

    public String generateAccessToken(User user) {
        return generateToken(user.getId(), expiration);
    }

    public String generateRefreshToken(User user) {
        return generateToken(user.getId(), refreshExpiration);
    }

    private String generateToken(Long userId, long expirationTime) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            return isTokenTimeValid(accessToken);
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            throw new BusinessException(ErrorMessages.Invalid_Token);
        }
    }

    public String renewAccessTokenUsingRefreshToken(String refreshToken)  {
        try {
            Claims claims = parseToken(refreshToken).getBody();
            Long userId = Long.valueOf(claims.getSubject());
            return generateToken(userId, expiration);
        } catch (JwtException e) {
            throw new BusinessException(ErrorMessages.Invalid_Token);
        }
    }

    public Long getUserIdByParseToken(String token){
        Jws<Claims> claimsJws = parseToken(token);
        String subject = claimsJws.getBody().getSubject();
        return Long.parseLong(subject);
    }

    private Jws<Claims> parseToken(String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
    }

    private boolean isTokenTimeValid(String token) throws JwtException {
        Jws<Claims> claims = parseToken(token);
        return !claims.getBody().getExpiration().before(new Date());
    }
}

