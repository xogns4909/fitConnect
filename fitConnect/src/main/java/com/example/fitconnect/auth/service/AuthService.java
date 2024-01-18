package com.example.fitconnect.auth.service;

import com.example.fitconnect.auth.dto.GoogleInfoDto;
import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;

import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private String clientId;

    public AuthService(Environment env) {
        this.clientId = env.getProperty("security.oauth2.client.registration.google.client-id");
    }


    public GoogleInfoDto authenticate(String token) {
        return getGoogleUserInfo(token);

    }

    private GoogleInfoDto getGoogleUserInfo(String token) {
        try {
            GoogleIdTokenVerifier verifier = getGoogleIdTokenVerifier();
            GoogleIdToken idToken = verifier.verify(token.substring(1, token.length() - 1));
            Payload payload = idToken.getPayload();
            return getGoogleInfoDto(payload);

        } catch (GeneralSecurityException | IOException e) {
            throw new BusinessException(ErrorMessages.SECURITY_EXCEPTION);
        }

    }

    private GoogleInfoDto getGoogleInfoDto(Payload payload) {
        String email = payload.getEmail();
        String name = payload.get("name").toString();
        String pictureUrl = payload.get("picture").toString();
        return new GoogleInfoDto(email, name, pictureUrl);
    }

    private GoogleIdTokenVerifier getGoogleIdTokenVerifier() {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();
        return verifier;
    }
}


