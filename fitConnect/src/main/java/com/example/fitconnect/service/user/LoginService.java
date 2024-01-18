package com.example.fitconnect.service.user;

import com.example.fitconnect.auth.dto.GoogleInfoDto;
import com.example.fitconnect.auth.service.JwtService;
import com.example.fitconnect.domain.user.domain.User;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final UserFindService userFindService;
    private final UserRegisterService userRegisterService;
    private final JwtService jwtService;

    public Map<String, String> processUserLogin(GoogleInfoDto googleInfoDto) {
        User user = getOrCreateUser(googleInfoDto);
        Map<String, String> stringStringMap = generateAuthTokens(user);
        log.info("accessToken , RefreshToken : {} ", stringStringMap);
        return stringStringMap;
    }

    private User getOrCreateUser(GoogleInfoDto googleInfoDto) {
        return userFindService.findUserByEmail(googleInfoDto.getEmail())
                .orElseGet(() -> userRegisterService.registerUser(googleInfoDto.toUserRegisterDto()));
    }

    private Map<String, String> generateAuthTokens(User user) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", jwtService.generateAccessToken(user));
        tokens.put("refreshToken", jwtService.generateRefreshToken(user));
        return tokens;
    }
}