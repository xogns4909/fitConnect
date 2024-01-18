package com.example.fitconnect.controller.user;

import com.example.fitconnect.auth.dto.GoogleInfoDto;
import com.example.fitconnect.auth.service.AuthService;
import com.example.fitconnect.service.user.LoginService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final LoginService loginService;
    private final AuthService authService;

    @PostMapping("/api/auth/google")
    public ResponseEntity<Map<String,String>> login(@RequestBody String token) {
        GoogleInfoDto authenticate = authService.authenticate(token);
        Map<String, String> stringStringMap = loginService.processUserLogin(authenticate);
        return ResponseEntity.ok(stringStringMap);

    }
}