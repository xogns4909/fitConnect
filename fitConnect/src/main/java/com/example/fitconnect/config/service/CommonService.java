package com.example.fitconnect.config.service;

import com.example.fitconnect.auth.service.JwtService;
import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final JwtService jwtService;

    public Long extractUserIdFromSession(HttpSession session) {

        try {
            String accessToken = (String) session.getAttribute("accessToken");
            return jwtService.getUserIdByParseToken(accessToken);
        } catch (RuntimeException e) {
            throw new BusinessException(ErrorMessages.Invalid_Token);
        }

    }
}