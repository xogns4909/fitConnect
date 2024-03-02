package com.example.fitconnect.service.user;

import com.example.fitconnect.auth.dto.GoogleInfoDto;
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

    public Long processUserLogin(GoogleInfoDto googleInfoDto) {
        User user = getOrCreateUser(googleInfoDto);

        return user.getId();
    }

    private User getOrCreateUser(GoogleInfoDto googleInfoDto) {
        return userFindService.findUserByEmail(googleInfoDto.getEmail())
                .orElseGet(() -> userRegisterService.registerUser(googleInfoDto.toUserRegisterDto()));
    }

}